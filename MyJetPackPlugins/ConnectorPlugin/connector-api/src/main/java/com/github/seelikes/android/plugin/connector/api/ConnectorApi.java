package com.github.seelikes.android.plugin.connector.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class ConnectorApi {
    private static Map<Class<?>, Object> classObjMap = new ConcurrentHashMap<>();
    private static Map<Class<?>, List<ConnectorInitializer<?>>> classInitializerMap = new ConcurrentHashMap<>();
    private static Map<Class<?>, ConnectorBean> superClassConnectorMap = new ConcurrentHashMap<>();

    private static <T> T newInstanceFinal(Class<T> targetClass, Object... args) {
        if (targetClass == null) {
            return null;
        }
        Constructor<?>[] constructors = targetClass.getConstructors();
        if (constructors.length == 0) {
            Method[] methods = targetClass.getDeclaredMethods();
            if (methods.length > 0) {
                for (Method method : methods) {
                    if ((method.getModifiers() & Modifier.STATIC) == Modifier.STATIC && (method.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC) {
                        boolean accessible = method.isAccessible();
                        try {
                            method.setAccessible(true);
                            return (T) method.invoke(null, args);
                        }
                        catch (Throwable ignored) {

                        }
                        finally {
                            method.setAccessible(accessible);
                        }
                    }
                }
            }
            Field[] fields = targetClass.getDeclaredFields();
            if (fields.length > 0) {
                for (Field field : fields) {
                    if (field.getType() == targetClass && ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC) && ((field.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC)) {
                        boolean accessible = field.isAccessible();
                        try {
                            field.setAccessible(true);
                            return (T) field.get(null);
                        }
                        catch (Throwable ignored) {

                        }
                        finally {
                            field.setAccessible(accessible);
                        }
                    }
                }
            }
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
        List<ConnectorInitializer<?>> ci = classInitializerMap.get(superClass);
        if (ci != null && !ci.isEmpty()) {
            for (int i = 0; i < ci.size(); ++i) {
                try {
                    ConnectorInitializer<?> initializer = ci.get(i);
                    if (initializer != null) {
                        obj = initializer.initialize(args);
                        if (obj != null && superClass.isAssignableFrom(obj.getClass())) {
                            return (T) obj;
                        }
                    }
                }
                catch (Throwable throwable) {

                }
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
