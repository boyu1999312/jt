package com.jt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.Jedis;

//标识Redis的配置类
@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {

    @Value("${jedis.host}")
    private String host;
    @Value("${jedis.port}")
    private Integer port;

    @Bean
    public Jedis jedis(){
        return new Jedis(host, port);
    }




}
