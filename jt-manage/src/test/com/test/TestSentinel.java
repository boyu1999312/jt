package com.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

public class TestSentinel {

    //测试哨兵get/set操作
    @Test
    public void test01(){
        //masterName 代表主机的变量名称 --> mymaster
        //sentinels Set<String> Ip:Port
        Set<String> sentinels = new HashSet<>();
        sentinels.add("192.168.227.130:26379");

        JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinels);

        //获取主机
        Jedis jedis = sentinelPool.getResource();
        jedis.set("cc", "6381cc");
        System.out.println(jedis.get("cc"));
        jedis.close(); //释放资源
    }
}
