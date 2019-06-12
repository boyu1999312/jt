package com.jt.aspect;

import com.jt.annotation.Cache_find;
import com.jt.enu.KEY_ENUM;
import com.jt.util.ObjectMapperUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;


@Aspect
@Component
public class RedisCache {

    //@Autowired(required = false)
    //private ShardedJedis jedisCluster;
    @Autowired(required = false)
    private JedisCluster jedisCluster;

    @Pointcut("@annotation(com.jt.annotation.Cache_find)")
    public void doRedisCache(){}

    @Around("doRedisCache() && @annotation(cache_find)")
    public Object around(ProceedingJoinPoint point, Cache_find cache_find) {
        Object data = null;
        String key = getKey(point, cache_find);
        String result = jedisCluster.get(key);
        try {
            if(StringUtils.isEmpty(result)){
                data =  point.proceed();
                String json = ObjectMapperUtil.toJson(data);
                if (cache_find.seconds() == 0)
                    jedisCluster.set(key, json);
                else
                    jedisCluster.setex(key, cache_find.seconds(), json);
                System.out.println("业务查询数据库");
            }else{
                data = ObjectMapperUtil.toObject(result, getReturnType(point));
                System.out.println("业务查询Redis缓存");
            }
        }catch (Throwable e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return data;
    }

    private String getKey(ProceedingJoinPoint point, Cache_find cache_find){
        KEY_ENUM key_enum = cache_find.keyType();
        if(key_enum.equals(KEY_ENUM.EMPTY)){
            return cache_find.value();
        }else {
            String key = cache_find.value();
            return key += "_" + point.getArgs()[0];
        }
    }

    private Class getReturnType(ProceedingJoinPoint point){
        MethodSignature signature = (MethodSignature) point.getSignature();
        return signature.getReturnType();
    }




}
