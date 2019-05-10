package com.example.myjetpackapplication.basic

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.example.myjetpackapplication.utils.runIfClassHasAnnotation
import com.gyf.immersionbar.ktx.immersionBar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class BasicActivity<C : BasicActivity<C, M, B>, M : BasicHostViewModel<M, C, B>, B : ViewDataBinding> : AppCompatActivity(), BasicInitView<B> {
    @Suppress("MemberVisibilityCanBePrivate")
    protected lateinit var model: M

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runIfClassHasAnnotation(Subscribe::class.java) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }
        model = initModel(savedInstanceState)
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