package com.github.seelikes.android.cache

import android.os.Handler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.java.lib.oil.lang.StringManager
import java.lang.ref.SoftReference
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap
import javax.crypto.Cipher
import kotlin.random.Random

/**
 * Created by liutiantian on 2019-12-25 20:22 星期三
 */
object Cache {
    private val cache = ConcurrentHashMap<Any, CacheItem>()
    private val newObservers: MutableMap<(Any, Any?) -> Unit, Pair<LifecycleOwner?, LifecycleObserver?>?> = mutableMapOf()
    private val changeObservers: MutableMap<(Any, Any?, Any?) -> Unit, Pair<LifecycleOwner?, LifecycleObserver?>?> = mutableMapOf()
    private val removeObservers: MutableMap<(Any, Any?) -> Unit, Pair<LifecycleOwner?, LifecycleObserver?>?> = mutableMapOf()

    private val handler = Handler()

    internal fun init() {

    }

    fun <T : Any> get(key: Any, def: T?): T? {
        return extract(
            cache[key]?.value,
            def
        )
    }

    @JvmOverloads
    fun <T : Any> strong(key: Any, value: T?, shell: LifecycleOwner? = null, timeout: Long = -1, changeObserver: ((Any, Any?, Any?) -> Unit)? = null, removeObserver: ((Any, Any?) -> Unit)? = null) {
        val cacheItem = CacheItem()
        cacheItem.value = value
        cacheItem.timeout = timeout
        cacheItem.shell = shell
        cacheItem.changeObserver = changeObserver
        cacheItem.removeObserver = removeObserver
        saveSkeleton(
            key,
            value,
            cacheItem
        )
    }

    @JvmOverloads
    fun <T : Any> soft(key: Any, value: T?, shell: LifecycleOwner? = null, timeout: Long = -1, changeObserver: ((Any, Any?, Any?) -> Unit)? = null, removeObserver: ((Any, Any?) -> Unit)? = null) {
        val cacheItem = CacheItem()
        cacheItem.value = SoftReference<T>(value)
        cacheItem.timeout = timeout
        cacheItem.shell = shell
        cacheItem.changeObserver = changeObserver
        cacheItem.removeObserver = removeObserver
        saveSkeleton(
            key,
            value,
            cacheItem
        )
    }

    @JvmOverloads
    fun <T : Any> weak(key: Any, value: T?, shell: LifecycleOwner? = null, timeout: Long = -1, changeObserver: ((Any, Any?, Any?) -> Unit)? = null, removeObserver: ((Any, Any?) -> Unit)? = null) {
        val cacheItem = CacheItem()
        cacheItem.value = WeakReference<T>(value)
        cacheItem.timeout = timeout
        cacheItem.shell = shell
        cacheItem.changeObserver = changeObserver
        cacheItem.removeObserver = removeObserver
        saveSkeleton(
            key,
            value,
            cacheItem
        )
    }

    fun remove(key: Any? = null) {
        when (key) {
            null -> {
                val iterator = cache.iterator()
                for (entry in iterator) {
                    entry.value.removeRunnable?.let { handler.removeCallbacks(it) }
                    entry.value.lifecycleObserver?.let { entry.value.shell?.lifecycle?.removeObserver(it) }
                    val value = extract(
                        entry.value.value,
                        null
                    )
                    entry.value.removeObserver?.invoke(entry.key, value)
                    removeObservers.forEach {
                        it.key(entry.key, value)
                    }
                    iterator.remove()
                }
            }
            else -> {
                val oldItem = cache.remove(key)
                oldItem?: return
                oldItem.removeRunnable?.let { handler.removeCallbacks(it) }
                oldItem.lifecycleObserver?.let { oldItem.shell?.lifecycle?.removeObserver(it) }
                val value = extract<Any>(
                    oldItem.value,
                    null
                )
                oldItem.removeObserver?.invoke(key, value)
                removeObservers.forEach {
                    it.key(key, value)
                }
            }
        }
    }

    fun registerNewObserver(newObserver: (Any, Any?) -> Unit, shell: LifecycleOwner? = null) {
        when (shell) {
            null -> newObservers[newObserver] = null
            else -> {
                val lifecycleObserver = object : LifecycleObserver {
                    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                    fun onDestroy() {
                        unregisterNewObserver(
                            newObserver
                        )
                    }
                }
                newObservers[newObserver] = shell to lifecycleObserver
            }
        }
    }

    fun unregisterNewObserver(newObserver: ((Any, Any?) -> Unit)? = null) {
        when (newObserver) {
            null -> {
                val iterator = newObservers.iterator()
                for (entry in iterator) {
                    iterator.remove()
                    entry.value?.second?.let { entry.value?.first?.lifecycle?.removeObserver(it) }
                }
            }
            else -> {
                realUnregisterNewReceiver(
                    newObserver,
                    newObservers[newObserver]
                )
                newObservers[newObserver]?.second?.let { newObservers[newObserver]?.first?.lifecycle?.removeObserver(it) }
                if (newObservers[newObserver] != null) {
                    newObservers.remove(newObserver)
                }
            }
        }
    }

    fun registerChangeObserver(changeObserver: (Any, Any?, Any?) -> Unit, shell: LifecycleOwner? = null) {
        when (shell) {
            null -> changeObservers[changeObserver] = null
            else -> {
                val lifecycleObserver = object : LifecycleObserver {
                    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                    fun onDestroy() {
                        unregisterChangeObserver(
                            changeObserver
                        )
                    }
                }
                changeObservers[changeObserver] = shell to lifecycleObserver
            }
        }
    }

    fun unregisterChangeObserver(changeObserver: ((Any, Any?, Any?) -> Unit)? = null) {
        when (changeObserver) {
            null -> {
                val iterator = changeObservers.iterator()
                for (entry in iterator) {
                    iterator.remove()
                    entry.value?.second?.let { entry.value?.first?.lifecycle?.removeObserver(it) }
                }
            }
            else -> {
                changeObservers[changeObserver]?.second?.let { changeObservers[changeObserver]?.first?.lifecycle?.removeObserver(it) }
                if (changeObservers[changeObserver] != null) {
                    changeObservers.remove(changeObserver)
                }
            }
        }
    }

    fun registerRemoveObserver(removeObserver: (Any, Any?) -> Unit, shell: LifecycleOwner? = null) {
        when (shell) {
            null -> removeObservers[removeObserver] = null
            else -> {
                val lifecycleObserver = object : LifecycleObserver {
                    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                    fun onDestroy() {
                        unregisterRemoveObserver(
                            removeObserver
                        )
                    }
                }
                removeObservers[removeObserver] = shell to lifecycleObserver
            }
        }
    }

    fun unregisterRemoveObserver(removeObserver: ((Any, Any?) -> Unit)? = null) {
        when (removeObserver) {
            null -> {
                val iterator = removeObservers.iterator()
                for (entry in iterator) {
                    iterator.remove()
                    entry.value?.second?.let { entry.value?.first?.lifecycle?.removeObserver(it) }
                }
            }
            else -> {
                removeObservers[removeObserver]?.second?.let { removeObservers[removeObserver]?.first?.lifecycle?.removeObserver(it) }
                if (removeObservers[removeObserver] != null) {
                    removeObservers.remove(removeObserver)
                }
            }
        }
    }

    /**
     * 生成随机键
     */
    fun generateRandomKey(len: Int = 16): String {
        val randomBytesStr = StringManager.getInstance().base64(Random.nextBytes(len), Cipher.ENCRYPT_MODE)
        var res = ""
        for (i in 0 until len) {
            res += randomBytesStr[Random.nextInt(0, randomBytesStr.size)]
        }
        return res
    }

    fun destroy() {
        unregisterNewObserver()
        unregisterChangeObserver()
        unregisterRemoveObserver()
        remove()
    }

    private fun realUnregisterNewReceiver(newObserver: (Any, Any?) -> Unit, cacheItem: Pair<LifecycleOwner?, LifecycleObserver?>?) {
        cacheItem?.second?.let {
            cacheItem.first?.lifecycle?.removeObserver(it)
        }
    }

    private fun <T: Any> saveSkeleton(key: Any, value: T?, cacheItem: CacheItem) {
        val oldItem = cache[key]
        oldItem?.removeRunnable?.let {
            handler.removeCallbacks(it)
        }
        oldItem?.lifecycleObserver?.let {
            oldItem.shell?.lifecycle?.removeObserver(it)
        }

        cache[key] = cacheItem
        if (cacheItem.timeout > 0) {
            cacheItem.removeRunnable = Runnable {
                remove(key)
            }
            handler.post(cacheItem.removeRunnable)
        }
        cacheItem.shell?.let {
            val lifecycleObserver = object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    remove(key)
                }
            }
            cacheItem.lifecycleObserver = lifecycleObserver
            cacheItem.shell?.lifecycle?.addObserver(lifecycleObserver)
        }

        oldItem?.changeObserver?.let {
            val oldValue = extract(
                oldItem.value,
                null
            )
            it(key, value, oldValue)
            changeObservers.forEach { changeObserver ->
                changeObserver.key(key, value, oldValue)
            }
        }
        oldItem ?: newObservers.forEach {
            it.key(key, value)
        }
    }

    private fun <T: Any> extract(value: Any?, def: T?): T? {
        value?.let {
            return when (it) {
                is SoftReference<*> -> it.get() as? T?
                is WeakReference<*> -> it.get() as? T?
                else -> it as T?
            }
        }
        return def
    }
}