package com.github.seelikes.android.plugin.connector.api.initializer;

import com.github.seelikes.android.plugin.connector.api.ConnectorInitializer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("unchecked")
public class InstanceInitializer<T> implements ConnectorInitializer<T> {
    private Method method;

    public InstanceInitializer(Method method) {
        this.method = method;
    }

    @Override
    public T initialize(Object... args) {
        if (method != null) {
            try {
                boolean accessible = method.isAccessible();
                try {
                    method.setAccessible(true);
                    return (T) method.invoke(null, args);
                } finally {
                    method.setAccessible(accessible);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
