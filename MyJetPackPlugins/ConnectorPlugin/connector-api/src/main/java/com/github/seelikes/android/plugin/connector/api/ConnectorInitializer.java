package com.github.seelikes.android.plugin.connector.api;

/**
 * Created by liutiantian on 2020-06-29
 * Email: liutiantian01@corp.netease.com
 */
public interface ConnectorInitializer<T> {
    T initialize(Object... args);
}
