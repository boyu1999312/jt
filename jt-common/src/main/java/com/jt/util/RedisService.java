package com.jt.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Service
public class RedisService {
    @Autowired(required = false)//调用时才注入
    private JedisSentinelPool pool;

    //封装方法 get/set
    public String get(String key){
        Jedis jedis = pool.getResource();
        String result = jedis.get(key);
        jedis.close();
        return result;
    }
    //set
    public String set(String key, String json){
        Jedis jedis = pool.getResource();
        String result = jedis.set(key, json);
        jedis.close();
        return result;
    }
    //setex
    public String setex(String key, int seconds, String json){
        Jedis jedis = pool.getResource();
        String result = jedis.setex(key, seconds, json);
        jedis.close();
        return result;
    }
}
