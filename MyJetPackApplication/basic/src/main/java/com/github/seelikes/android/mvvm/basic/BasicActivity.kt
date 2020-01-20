package com.github.seelikes.android.mvvm.basic

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.github.seelikes.android.mvvm.basic.utils.runIfClassHasAnnotation
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class BasicActivity<C : BasicActivity<C, M, B>, M : BasicHostViewModel<M, C, B>, B : ViewDataBinding> : AppCompatActivity(), BasicInitView<B> {
    /**
     * Model对象
     */
    protected lateinit var model: M

    /**
     * ViewBinding对象
     */
    protected lateinit var binding: B

    /**
     * 协程启动上下文
     */
    protected val activityScope: CoroutineScope by lazy {
        val job = SupervisorJob()
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy(activity: LifecycleOwner) {
                activity.lifecycle.removeObserver(this)
                job.cancel()
            }
        })
        CoroutineScope(Dispatchers.Main + job)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runIfClassHasAnnotation(Subscribe::class.java) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
        model = initModel(savedInstanceState)
        binding = model.binding
        if (useImmersionBar()) {
            immersionBar {
                statusBarColor(provideStatusBarColor())
                navigationBarColor(provideStatusBarColor())
            }
        }
        initView(model.binding)
    }

    @CallSuper
    override fun onDestroy() {
        model.binding.unbind()
        runIfClassHasAnnotation(Subscribe::class.java) {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this)
            }
        }
        super.onDestroy()
    }

    /**
     * 初始化model
     */
    abstract fun initModel(savedInstanceState: Bundle?): M

    /**
     * 是否采用沉浸式状态栏
     * true 采用沉浸式状态栏
     * false 不采用或自行实现沉浸式状态栏
     */
    open fun useImmersionBar(): Boolean = true

    @ColorRes
    open fun provideStatusBarColor(): Int = android.R.color.transparent
}