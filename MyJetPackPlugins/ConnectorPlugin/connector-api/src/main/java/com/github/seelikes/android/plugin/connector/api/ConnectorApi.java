package com.github.seelikes.android.plugin.connector.api;

import androidx.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class ConnectorApi {
    private static Map<Class<?>, Object> classObjMap = new ConcurrentHashMap<>();
    private static Map<Class<?>, ConnectorBean> superClassConnectorMap = new ConcurrentHashMap<>();

    @Nullable
    private static <T> T newInstanceFinal(Class<T> targetClass, Object... args) {
        if (targetClass == null) {
            return null;
        }
        Constructor<?>[] constructors = targetClass.getConstructors();
        if (constructors.length == 0) {
            throw new IllegalStateException("no constructor for " + targetClass.getName());
        }
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterTypes().length == args.length) {
                if ((constructor.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC) {
                    boolean accessible = constructor.isAccessible();
                    constructor.setAccessible(accessible);
                    try {
                        return (T) constructor.newInstance(args);
                    }
                    catch (Throwable ignored) {

                    }
                    finally {
                        constructor.setAccessible(accessible);
                    }
                }
            }
        }
        return null;
    }

    @Nullable
    private static <T> T newInstance(Class<? super T> superClass, Object... args) {
        ConnectorBean bean = superClassConnectorMap.get(superClass);
        if (bean == null) {
            return null;
        }
        Class<?> targetClass = bean.targetClass;
        if (targetClass == null) {
            return null;
        }
        if (superClass.isAssignableFrom(targetClass)) {
            T value = (T) newInstanceFinal(targetClass, args);
            if (bean.singleton && value != null) {
                classObjMap.put(superClass, value);
            }
            return value;
        }
        throw new IllegalStateException("superClass has a mapping class which is not subclass");
    }

    private static <T> T getFromObjMap(Class<T> superClass, Object... args) {
        if (superClass == null) {
            return null;
        }
        Object obj = classObjMap.get(superClass);
        if (obj != null) {
            if (superClass.isAssignableFrom(obj.getClass())) {
                return superClass.cast(obj);
            }
            else {
                throw new IllegalStateException("saved instance can not be cast to superClass");
            }
        }
        return null;
    }

    /**
     * fetch instance for superClass
     * @param superClass superClass, interface
     * @param args parameters for create new instance
     * @param <T> target type
     * @return instance
     */
    public static <T> T get(Class<T> superClass, Object... args) {
        if (superClass == null) {
            return null;
        }
        T value = getFromObjMap(superClass, args);
        if (value != null) {
            return value;
        }
        return newInstance(superClass, args);
    }

    /**
     * save instance for superClass
     * @param superClass superClass, interface
     * @param obj instance for superClass
     * @param <T> target type
     * @return old instance of target type
     */
    public <T> T put(Class<? super T> superClass, T obj) {
        try {
            return (T) classObjMap.put(superClass, obj);
        }
        catch (Throwable throwable) {

        }
        throw new IllegalArgumentException("saved obj is not the return type");
    }

    /**
     * release all created instances
     */
    public static void destroy() {
        classObjMap.clear();
    }
}
