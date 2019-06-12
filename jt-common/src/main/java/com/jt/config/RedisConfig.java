package com.jt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


//标识Redis的配置类
@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {

    //@Value("${jedis.sentinels}")
    //private String jedisSentinelNodes;
    //@Value("${jedis.sentinel.masterName}")
    //private String masterName;

    //@Bean
    //public Jedis jedis(){
    //    return new Jedis(host, port);
    //}


    //@Value("${jedis.host}")
    //private String host;
    //@Value("${jedis.port}")
    //private Integer port;
    @Value("${jedis.nodes}")
    private String nodes;

    @Bean
    public JedisCluster jedisCluster(){
        String[] ipPorts = nodes.split(",");

        Set<HostAndPort> infoList = new HashSet<>();
        for (String ipPort : ipPorts) {
            String[] ip = ipPort.split(":");
            infoList.add(new HostAndPort(ip[0], Integer.valueOf(ip[1])));
        }
        return new JedisCluster(infoList);
    }

    /*@Bean
    public JedisSentinelPool getJedisSentinelPool(){
        Set<String> sentinels = new HashSet<>();
        sentinels.add(jedisSentinelNodes);
        return new JedisSentinelPool(masterName, sentinels);
    }*/

}
