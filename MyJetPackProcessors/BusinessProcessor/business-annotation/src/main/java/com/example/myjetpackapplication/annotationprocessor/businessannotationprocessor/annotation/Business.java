package com.example.myjetpackapplication.annotationprocessor.businessannotationprocessor.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liutiantian on 2019-12-21 15:24 星期六
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface Business {
    /**
     * 标题名称
     */
    String title();

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
