package com.isik.control;


import com.isik.api.Constants;
import com.isik.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author @fisik
 */
@RestController
@RequestMapping("producer")
@Profile(Constants.EDGE)
public class ProducerControl {

    @Autowired
    private Producer producer;

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE,path = "/start")
    public String start() {
        producer.start();
        return "STARTED";
    }

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE,path = "/stop")
    public String stop() {
        producer.stop();
        return "STOPPED";
    }

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE,path = "/count")
    public long count() {
        return producer.getDataCount();
    }
}
