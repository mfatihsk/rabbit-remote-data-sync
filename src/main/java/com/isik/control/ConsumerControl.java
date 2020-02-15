package com.isik.control;


import com.isik.api.Constants;
import com.isik.api.ConsumerValues;
import com.isik.receiver.CenterReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author @fisik
 */
@RestController
@RequestMapping("consumer")
@Profile(Constants.CENTER)
public class ConsumerControl {

    @Autowired
    private CenterReceiver centerReceiver;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ConsumerValues start() {
        return centerReceiver.getConsumerValues();
    }

}
