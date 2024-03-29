package com.olixie.animalshelper;

import com.olixie.animalshelper.util.ProjectConstant;
import com.olixie.animalshelper.util.RedisUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTest {


    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisUtil redisUtil;

    @Test
    public void baseTest(){
        stringRedisTemplate.opsForValue().set("name","zhangsan", 10, TimeUnit.SECONDS);
        String name = stringRedisTemplate.opsForValue().get("name");
        System.out.println(name);
    }


    @Test
    public void baseTest2(){
        redisUtil.set("name","lisi", 10L, TimeUnit.SECONDS);
        String name = redisUtil.get("name");
        System.out.println(name);
    }


    @Test
    public void setTest(){

        Boolean member = redisUtil.isMember("like_set_-627499007", "-598138879");
        System.out.println(member);
        member = redisUtil.isMember("like_set_-627499007","-627499007");
        System.out.println(member);

    }
}
