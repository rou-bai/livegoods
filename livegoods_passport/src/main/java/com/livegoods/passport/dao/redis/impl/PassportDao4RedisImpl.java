package com.livegoods.passport.dao.redis.impl;

import com.livegoods.passport.dao.redis.PassportDao4Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Repository
public class PassportDao4RedisImpl implements PassportDao4Redis {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String getValidateCode(String key) {
        Object value = stringRedisTemplate.opsForValue().get(key);
        if(value == null){
            return null;
        }
        return value.toString();
    }

    @Override
    public void setValidateCode(String key, String validateCode, long times, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, validateCode, times, unit);
    }

    @Override
    public void removeValidateCode(String key) {
        stringRedisTemplate.delete(key);
    }
}
