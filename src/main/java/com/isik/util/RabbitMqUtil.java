package com.isik.util;

import com.isik.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * @author fisik
 */
public class RabbitMqUtil {
    public static final String SHOVEL_PREFIX    = "shovel_";

    private static final Logger LOGGER          = LoggerFactory.getLogger(RabbitMqUtil.class);

    private static final String AMQP_ADDRESS    = "amqp://%s:%s@%s";

    private static final String HTTP_TEMPLATE   = "http://{0}/api/parameters/shovel/%2f/{1}";

    private static final String SHOVEL_TEMPLATE = "{\"value\":{\"src-protocol\": \"amqp091\", \"src-uri\":  \"%s\", \"src-queue\": \"%s\", \"dest-protocol\": \"amqp091\", \"dest-uri\": \"%s\", \"dest-queue\": \"%s\" , \"src-prefetch-count\": %s}}";

    private RabbitMqUtil() {
    }

    /**
     * creates shovel from source to destination
     * @param props
     * @param appProps
     * @param queueName
     * @return
     */
    public static boolean createShovel(RabbitProperties props, AppProperties appProps, String queueName) {

        String localAmqpAddress = concat(props.getHost(), ":", props.getPort());
        String remoteAmqpAddress = concat(appProps.getRemoteRabbitIp(), ":", appProps.getRemoteRabbitPort());

        String localAmqp = getAmqpAddress(props.getUsername(), props.getPassword(), localAmqpAddress);
        String remoteAmqp = getAmqpAddress(appProps.getRemoteRabbitUserName(), appProps.getRemoteRabbitPassword(), remoteAmqpAddress);
        String httpCommand = String.format(SHOVEL_TEMPLATE, localAmqp , queueName, remoteAmqp ,queueName, 100);

        String localAddress = concat(props.getHost() , ":" , appProps.getSourceRabbitHttpPort()) ;
        String shovelName = concat(SHOVEL_PREFIX, queueName);

        String server = MessageFormat.format(HTTP_TEMPLATE, localAddress, shovelName);
        try {
            HttpHeaders headers = getHttpHeaders(props.getUsername(), props.getPassword());
            RestTemplate rest = new RestTemplate();
            rest.exchange(new URI(server), HttpMethod.PUT, new HttpEntity<>(httpCommand, headers), String.class);
            return true;
        } catch (URISyntaxException e) {
            LOGGER.error("SHOVEL creation error for : {}",shovelName, e);
            return false;
        }
    }

    private static HttpHeaders getHttpHeaders(String userName,String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.setBasicAuth(userName, password);
        return headers;
    }

    private static String getAmqpAddress(String userName, String pass, String amqpAddress) {
        return String.format(AMQP_ADDRESS,userName,pass,amqpAddress);
    }


    private static String concat(Object... args) {
        StringBuilder builder = new StringBuilder();
        Arrays.asList(args).forEach(builder::append);
        return builder.toString();
    }

}
