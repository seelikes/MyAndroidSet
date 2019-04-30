package com.example.myjetpackapplication.utils

/**
 * 在任何对象上反射调用SAM方法
 */
fun Any?.callWithParameter(host: Any) {
    this?.let {
        val methods = host.javaClass.declaredMethods
        for (method in methods) {
            val parameterTypes = method.parameterTypes
            if (parameterTypes.size == 1) {
                if (parameterTypes[0].isAssignableFrom(this.javaClass)) {
                    val accessible = method.isAccessible
                    try {
                        method.isAccessible = true
                        method.invoke(host, this)
                    }
                    finally {
                        method.isAccessible = accessible
                    }
                }
            }
        }
    }
}