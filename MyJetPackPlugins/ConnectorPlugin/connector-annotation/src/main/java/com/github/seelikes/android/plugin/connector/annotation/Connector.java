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
     * @return Interface Class, maybe parent class also
     */
    Class<?> value();

    /**
     * Single Instance across process
     * @return true if singleton, otherwise false
     */
    boolean singleton() default true;
}
