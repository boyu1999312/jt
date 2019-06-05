package com.jt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.List;

//标识Redis的配置类
@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {

    //@Value("${jedis.host}")
    //private String host;
    //@Value("${jedis.port}")
    //private Integer port;
    @Value("${jedis.nodes}")
    private String nodes;


    //@Bean
    //public Jedis jedis(){
    //    return new Jedis(host, port);
    //}

    @Bean
    public ShardedJedis shardedJedis(){
        String[] ipPorts = nodes.split(",");

        List<JedisShardInfo> infoList = new ArrayList<>();
        for (String ipPort : ipPorts) {
            System.out.println(ipPort);
            String[] ip = ipPort.split(":");

            JedisShardInfo info = new JedisShardInfo(ip[0], ip[1]);
            infoList.add(info);
        }
        return new ShardedJedis(infoList);
    }


}
