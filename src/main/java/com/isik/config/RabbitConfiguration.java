package com.isik.config;

import com.isik.api.Constants;
import com.isik.util.RabbitMqUtil;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * @author @fisik
 */
@Configuration
public class RabbitConfiguration implements RabbitListenerConfigurer {

    @Autowired
    private RabbitProperties    props;

    @Autowired
    private AppProperties       appProperties;

    @Autowired
    private AmqpAdmin           amqpAdmin;

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory   connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public RabbitListenerEndpointRegistrar endpointRegistrar(SimpleRabbitListenerContainerFactory containerFactory) {
        RabbitListenerEndpointRegistrar registrar = new RabbitListenerEndpointRegistrar();
        registrar.setEndpointRegistry(endpointRegistry());
        registrar.setContainerFactory(containerFactory);
        registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
        return registrar;
    }

    @Bean
    public RabbitListenerEndpointRegistry endpointRegistry() {
        return new RabbitListenerEndpointRegistry();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }


    @Bean
    public Queue outgoingQueue() {
        Map<String, Object> args = new HashMap<>();
        // The default exchange
        args.put("x-dead-letter-exchange", "");
        // Route to the dead letter queue when the TTL occurs
        args.put("x-dead-letter-routing-key", Constants.DEAD_LETTER_QUEUE);
        // TTL 10 seconds
        args.put("x-message-ttl", 10000);
        args.put("x-max-priority", 10);
        Queue queue = QueueBuilder.nonDurable(Constants.LIVE_QUEUE).withArguments(args).build();
        amqpAdmin.declareQueue(queue);

        RabbitMqUtil.createShovel(props, appProperties, queue.getName());
        return queue;
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(outgoingQueue()).to(exchange()).with(Constants.ROUTING_KEY);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(Constants.EXCHANGE);
    }

    @Bean
    public Queue deadLetterQueue() {
        Map<String, Object> args = new HashMap<>();
        Queue queue = QueueBuilder.durable(Constants.DEAD_LETTER_QUEUE).withArguments(args).build();
        RabbitMqUtil.createShovel(props, appProperties, queue.getName());
        return queue;
    }
}
