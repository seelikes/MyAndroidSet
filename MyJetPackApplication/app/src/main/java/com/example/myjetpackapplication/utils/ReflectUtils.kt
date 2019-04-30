package com.example.myjetpackapplication.utils

import android.util.Log

/**
 * 在任何对象上反射调用SAM方法
 */
fun Any?.callWithParameter(host: Any) {
    this?.let {
        Log.i("ReflectUtils", "Any?.callWithParameter.UL1200LP.DI1211, enter")
        val methods = host.javaClass.declaredMethods
        for (method in methods) {
            val parameterTypes = method.parameterTypes
            if (parameterTypes.size == 1) {
                Log.i("ReflectUtils", "Any?.callWithParameter.UL1200LP.DI1211, parameterTypes[0].isAssignableFrom(this.javaClass): ${parameterTypes[0].isAssignableFrom(this.javaClass)}")
                if (parameterTypes[0].isAssignableFrom(this.javaClass)) {
                    Log.i("ReflectUtils", "Any?.callWithParameter.UL1200LP.DI1211, method.name: ${method.name}; method.isAccessible: ${method.isAccessible}")
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