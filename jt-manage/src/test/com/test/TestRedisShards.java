package com.test;

import org.junit.Test;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.List;

// 测试Redis分片
public class TestRedisShards {

    @Test
    public void testShards(){
        List<JedisShardInfo> infoList = new ArrayList<>();
        JedisShardInfo info1 = new JedisShardInfo("192.168.227.130", 6379);
        JedisShardInfo info2 = new JedisShardInfo("192.168.227.130", 6380);
        JedisShardInfo info3 = new JedisShardInfo("192.168.227.130", 6381);
        infoList.add(info1);
        infoList.add(info2);
        infoList.add(info3);
        //操作分片Redis对象工具类
        ShardedJedis shardedJedis = new ShardedJedis(infoList);
        shardedJedis.set("abab", "abab");
        System.out.println(shardedJedis.get("abab"));

    }
}
