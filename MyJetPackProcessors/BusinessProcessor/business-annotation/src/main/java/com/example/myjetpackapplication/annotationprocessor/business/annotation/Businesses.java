package com.example.myjetpackapplication.annotationprocessor.business.annotation;

import java.lang.annotation.*;

/**
 * Created by liutiantian on 2020-01-17 10:18 星期五
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Businesses {
    /**
     * 注解容器
     */
    Business[] value();
}
