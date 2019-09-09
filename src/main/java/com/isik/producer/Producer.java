package com.isik.producer;

import com.isik.api.Constants;
import com.isik.api.Telemetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author fisik
 */
@Component
public class Producer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private static final Random RANDOM = new Random();

    @Autowired
    private RabbitTemplate          rabbitTemplate;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    private ScheduledFuture         scheduledFuture ;

    private byte[]  data;

    private long    dataCount = 0 ;

    @PostConstruct
    public void init(){
        fillDummyData();
    }

    @PreDestroy
    public void preDestroy(){
        stop();
    }

    public void start(){
        stop();
        scheduledFuture = taskScheduler.getScheduledExecutor().scheduleWithFixedDelay(this::send, 100, 10, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if(scheduledFuture != null){
            scheduledFuture.cancel(true);
            scheduledFuture = null;
        }
    }

    /**
     * send messages to live queue
     */
    public void send() {
        Telemetry paymentOrder = new Telemetry(dataCount
                , RANDOM.nextInt(Short.MAX_VALUE)
                , RANDOM.nextInt(Short.MAX_VALUE), System.currentTimeMillis(), data);
        rabbitTemplate.convertAndSend(Constants.EXCHANGE, Constants.ROUTING_KEY, paymentOrder);
        LOGGER.info("Sending payload {}", paymentOrder);
        dataCount++;
    }

    public long getDataCount() {
        return dataCount;
    }

    /**
     * fill dummy data in order to test network bandwidth
     */
    private void fillDummyData() {
        data = new byte[320];
        for (int i = 0; i < data.length; i++) {
            data[i] = 'x';
        }
    }

}
