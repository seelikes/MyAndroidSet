package com.github.seelikes.android.plugin.connector.api.initializer;

import com.github.seelikes.android.plugin.connector.api.ConnectorInitializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructorInitializer<T> implements ConnectorInitializer<T> {
    private Constructor<T> constructor;

    public ConstructorInitializer(Constructor<T> constructor) {
        this.constructor = constructor;
    }

    @Override
    public T initialize(Object... args) {
        try {
            return constructor.newInstance(args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
