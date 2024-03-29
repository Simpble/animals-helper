package com.olixie.animalshelper.rocketmq;


import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

//@Component
//@RocketMQMessageListener(consumerGroup = "TestGroup",topic = "TestTopic")
@Slf4j
public class PetConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("Received message : "+ message);
    }
}
