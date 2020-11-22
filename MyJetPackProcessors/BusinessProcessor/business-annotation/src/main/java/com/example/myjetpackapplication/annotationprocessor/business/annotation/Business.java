package com.example.myjetpackapplication.annotationprocessor.business.annotation;

import java.lang.annotation.*;

/**
 * Created by liutiantian on 2019-12-21 15:24 星期六
 */
@Repeatable(Businesses.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Business {
    /**
     * 标题名称
     */
    String title();

    /**
     * route路径
     * 如果配置此参数，则宿主上的Route注解将会忽略
     */
    String path() default "";

    /**
     * 容器标题名称
     */
    String parent() default "";

    /**
     * 优先级
     * 越小优先级越高
     */
    int priority() default 0;

    /**
     * 是否启用
     */
    boolean enable() default true;
}
