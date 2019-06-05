package com.jt.aspect;

import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.ShardedJedis;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Service
public class RedisCache {

    @Autowired
    private ShardedJedis shardedJedis;

    @Pointcut("@annotation(com.jt.annotation.TestOne)")
    public void doRedisCache(){}

    @Around("doRedisCache()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        List<EasyUITree> treeList = new ArrayList<>();
        String key = "ITEM_CAT_"+point.getArgs()[0];
        String result = shardedJedis.get(key);
        if(StringUtils.isEmpty(result)){
            treeList = (List<EasyUITree>) point.proceed();
            String json = ObjectMapperUtil.toJson(treeList);
            shardedJedis.setex(key, 7*24*3600, json);
            System.out.println("业务查询数据库");
        }else{
            treeList = ObjectMapperUtil.toObject(result, treeList.getClass());
            System.out.println("业务查询Redis缓存");
        }
        return treeList;
    }


}
