package com.github.seelikes.android.plugin.connector.annotation;

import java.lang.annotation.*;

/**
 * Created by liutiantian on 2020-06-21 12:26 星期日
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Connector {
    /**
     * Name Target
     */
    Class<?> value();

    /**
     * Single Instance across process
     */
    boolean singleton() default true;
}
