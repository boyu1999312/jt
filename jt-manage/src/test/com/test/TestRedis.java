package com.test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestRedis {

    // String类型操作方式  不成功=[redis.conf 配置文件, 防火墙]
    // IP, 端口号
    @Test
    public void testString(){
        Jedis jedis = new Jedis("192.168.227.130", 6379);
        String set = jedis.set("1902", "1902班");
        System.out.println("is: " + set);
        String value = jedis.get("1902");
        jedis.expire("1902", 10);
        System.out.println("value: " + value);
    }




    // 设定数据的超时方法
    // 分布式锁
    @Test
    public void testRedis() throws InterruptedException {
        Jedis jedis = new Jedis("192.168.227.130", 6379);
        jedis.setex("aa", 2, "aa");
        System.out.println(jedis.get("aa"));

        Thread.sleep(3000);

        //当key不存在时 操作正常 存在时 操作失败
        Long result = jedis.setnx("aa", "bb");
        System.out.println("获取输出数据：" + result + " : " + jedis.get("aa"));
    }

    @Test
    public void objectToJSON() throws IOException {
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(100L).setItemDesc("测试方法");

        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(itemDesc);
        System.out.println(str);


        ItemDesc itemDesc1 = mapper.readValue(str, ItemDesc.class);
        System.out.println(itemDesc1);
    }

    /**
     * 3.利用Redis保存业务数据
     * 数据库数据: 对象 Object
     */
    @Test
    public void testSetObject() throws JsonProcessingException {
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(100L).setItemDesc("测试方法");
        Jedis jedis = new Jedis("192.168.227.130", 6379);
        //1.实现对象转化JSON
        ObjectMapper mapper = new ObjectMapper();

        jedis.set("itemDesc", mapper.writeValueAsString(itemDesc));
        System.out.println(jedis.get("itemDesc"));
        System.out.println("{\"created\":null,\"updated\":null,\"itemId\":100,\"itemDesc\":\"测试方法\"}");

    }


    @Test
    public void testList() throws IOException {
        ItemDesc itemDesc = new ItemDesc().setItemId(100L).setItemDesc("测试方法1");
        ItemDesc itemDesc1 = new ItemDesc().setItemId(100L).setItemDesc("测试方法2");
        List<ItemDesc> list = new ArrayList<>();
        list.add(itemDesc);
        list.add(itemDesc1);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(list);
        System.out.println(json);

        //将数据保存到Redis
        Jedis jedis = new Jedis("192.168.227.130", 6379);
        jedis.set("itemDescList", json);

        String itemDescList = jedis.get("itemDescList");
        List list1 = mapper.readValue(itemDescList, list.getClass());
        System.out.println(list1);
    }

    //研究转换关系
    @Data
    @Accessors(chain = true)
    @JsonIgnoreProperties(ignoreUnknown = true) //转换对象时忽略未知属性
    class User implements Serializable {
        private Integer id;
        private String name;
    }

    //User转换JSON串
    /**
     *  1.找到get方法
     *  2.去掉get后首字母变小写 作为key
     *  3.get方法获取值
     *  4.拼接成字符串
     */

    @Test
    public void userTOJSON() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(new User().setId(100).setName("王珂"));

        System.out.println(value);

    }

    /**
     * 1.解析json串获取key
     * 2.根据class类型创建实例，调用相应的setKey方法
     * 3.生成对象
     * @throws IOException
     */
    @Test
    public void jsonToUser() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = new User().setId(100).setName("王珂");
        String value = mapper.writeValueAsString(user);
        User user1;
        user1 = mapper.readValue(value, User.class);
        System.out.println(user1);
    }


}
