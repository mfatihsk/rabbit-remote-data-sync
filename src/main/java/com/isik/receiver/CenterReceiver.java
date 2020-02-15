package com.isik.receiver;

import com.isik.api.Constants;
import com.isik.api.ConsumerValues;
import com.isik.api.Telemetry;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Kullanici : Fatih Isik
 * Tarih     : 2.02.2020
 * Saat      : 12:07
 * Aciklama  :
 */
@Component
@Profile(Constants.CENTER)
public class CenterReceiver {

    private int countLive = 0;

    private int count = 0 ;

    private ConsumerValues consumerValues;

    @PostConstruct
    public void postConstruct(){
        consumerValues = new ConsumerValues();
    }

    @RabbitListener(queues = "#{autoDeleteQueue.name}")
    public void receive1(Telemetry in) {
        consumerValues.setAnonymous(++count);
    }
    @RabbitListener(queues = Constants.LIVE_QUEUE)
    public void receive3(Telemetry in) {
        consumerValues.setCountLive(++countLive);
    }

    public ConsumerValues getConsumerValues() {
        return consumerValues;
    }
}
