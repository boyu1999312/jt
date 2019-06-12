package com.jt.annotation;

import com.jt.enu.KEY_ENUM;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache_find {
    String value() default "";                      //用户key
    KEY_ENUM keyType() default KEY_ENUM.AUTO;       //key类型
    int seconds() default 0;                        //缓存时间，默认永不过期
}
