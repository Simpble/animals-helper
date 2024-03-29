package com.olixie.animalshelper;

import com.olixie.animalshelper.rocketmq.PetProducer;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RocketMqTest {
    @Resource
    private PetProducer petProducer;

    @Test
    public void sendTest(){
        petProducer.sendMessage("TestTopic","测试消息发送！");
    }
}
