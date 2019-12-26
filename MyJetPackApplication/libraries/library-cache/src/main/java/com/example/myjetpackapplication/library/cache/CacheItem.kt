package com.example.myjetpackapplication.library.cache

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Created by liutiantian on 2019-12-25 20:22 星期三
 */
internal class CacheItem {
    /**
     * 值
     */
    var value: Any? = null

    /**
     * 宿主
     * 宿主在，则元素在
     */
    var shell: LifecycleOwner? = null

    /**
     * 超时
     */
    var timeout: Long = 0

    /**
     * 超时移除
     */
    internal var removeRunnable: Runnable? = null

    /**
     * 宿主生命周期监听
     */
    internal var lifecycleObserver: LifecycleObserver? = null

    /**
     * 变化监听
     */
    internal var changeObserver: ((Any, Any?, Any?) -> Unit)? = null

    /**
     * 删除监听
     */
    internal var removeObserver: ((Any, Any?) -> Unit)? = null
}