package com.example.myjetpackapplication.annotationprocessor.business.annotation;

import java.lang.annotation.*;

/**
 * Created by liutiantian on 2019-12-21 15:24 星期六
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Business {
    /**
     * 标题名称
     */
    String title() default "";

    /**
     * route路径
     * 如果配置此参数，则宿主上的Route注解将会忽略
     */
    String path();

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
