package com.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Map;

public class TestRedis2 {
    //测试 hash/list/事务控制

    @Test
    public void testHash(){
        Jedis jedis = new Jedis("192.168.227.130", 6379);
        jedis.hset("user1", "id", "200");
        String hget = jedis.hget("user1", "id");
        System.out.println(hget);
        Map<String, String> user = jedis.hgetAll("user");
        System.out.println(user);
    }


    //编写List集合
    @Test
    public void testList(){
        Jedis jedis = new Jedis("192.168.227.130", 6379);
        jedis.lpush("popo", "1","2","3","4","5");
        for (int i = 0; i < 5; i++) {
            System.out.println(jedis.rpop("popo"));
        }
    }

    //redis事务控制
    @Test
    public void testTx(){
        Jedis jedis = new Jedis("192.168.227.130", 6379);
        Transaction multi = jedis.multi();
        try {
            multi.set("ww", "www");
            multi.set("1902", "1902");
            multi.exec();
        }catch (Exception e){
            multi.discard();
        }
    }

}
