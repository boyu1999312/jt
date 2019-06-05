package com.jt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 封装对象与JSON转换的工具类
 */
public class ObjectMapperUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    //toJson
    public static String toJson(Object target){
        String json = null;
        try {
             json = MAPPER.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //将检查异常转换为运行时异常
            throw new RuntimeException(e);
        }
        return json;
    }

    //将JSON串转换成对象
    public static <T> T toObject(String json, Class<T> cla){
        T target = null;
        try {
            target = MAPPER.readValue(json, cla);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return target;
    }
}


