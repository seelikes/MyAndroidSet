package utils;

import androidx.annotation.Nullable;

import java.lang.reflect.Method;

public class MethodUtil {
    @Nullable
    public static String getRawKeyFrom(@Nullable Method method) {
        if (method != null) {
            StringBuilder rawKeyBuilder = new StringBuilder();
            rawKeyBuilder.append(method.getDeclaringClass().getName());
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length > 0) {
                for (Class<?> parameterType : parameterTypes) {
                    rawKeyBuilder.append("-");
                    rawKeyBuilder.append(parameterType.getName());
                }
            }
            return rawKeyBuilder.toString();
        }
        return null;
    }
}
