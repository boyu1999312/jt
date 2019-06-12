package com.test;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class TestJedisCluster {
    @Test
    public void test01(){
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.227.130", 7000));
        nodes.add(new HostAndPort("192.168.227.130", 7001));
        nodes.add(new HostAndPort("192.168.227.130", 7002));
        nodes.add(new HostAndPort("192.168.227.130", 7003));
        nodes.add(new HostAndPort("192.168.227.130", 7004));
        nodes.add(new HostAndPort("192.168.227.130", 7005));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("aaa", "bbbaaa1902");
        System.out.println(jedisCluster.get("aaa"));

    }
}
