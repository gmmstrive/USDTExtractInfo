package com.gikee.usdtcollect.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;

@Slf4j
@Component
public class KafkaSender<T> {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void kafkaSend(String topic, T obj) {
        String jsonObj = JSON.toJSONString(obj);
        ListenableFuture future = kafkaTemplate.send(topic, jsonObj);
        future.addCallback(new ListenableFutureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Produce: The message failed to be sent : {}", ex.getMessage());
            }

            @Override
            public void onSuccess(Object result) {
                //log.info("Produce: The message was sent successfully");
            }
        });
    }

    public void kafkaSend(String topic, List<T> obj) {
        for (T t : obj) {
            kafkaSend(topic, t);
        }
    }

}
