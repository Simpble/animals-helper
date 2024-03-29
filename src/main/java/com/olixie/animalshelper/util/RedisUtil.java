package com.olixie.animalshelper.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /*设置String类型数据的两种方法，为有无过期时间进行区分*/
    public void set(String key, String val) {
        redisTemplate.opsForValue().set(key, val);
    }

    public void set(String key, String val, Long time, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, val, time, unit);
    }

    /*根据指定key获取对象*/
    public String get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    /*对指定key进行延期*/
    public void expire(String key,Long time,TimeUnit unit){
        redisTemplate.expire(key,time,unit);
    }

    public Long getExpire(String key){
        return redisTemplate.getExpire(key);
    }

    public Boolean delete(String key){
        return redisTemplate.delete(key);
    }

    public Boolean sadd(String key, String value){
        Long add = redisTemplate.opsForSet().add(key, value);
        if (add > 0){
            return true;
        }
        return false;
    }

    public Boolean isMember(String key,String value){
        /*如果当前key不存在直接返回false*/
        if(Boolean.FALSE.equals(redisTemplate.hasKey(key))){
            return false;
        }
        return redisTemplate.opsForSet().isMember(key,value);
    }

    public Boolean sremove(String key,String value){
        Long remove = redisTemplate.opsForSet().remove(key, value);
        if (remove > 0) {
            return true;
        }
        return false;
    }
}
