package com.example.myjetpackapplication.basic

import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.gyf.immersionbar.ktx.immersionBar

abstract class BasicActivity<C : BasicActivity<C, M, B>, M : BasicHostViewModel<M, C, B>, B : ViewDataBinding> : AppCompatActivity(), BasicInitView<B> {
    @Suppress("MemberVisibilityCanBePrivate")
    protected lateinit var model: M

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = initModel(savedInstanceState)
        if (useImmersionBar()) {
            immersionBar {
                statusBarColor(provideStatusBarColor())
                navigationBarColor(provideStatusBarColor())
            }
        }
        initView(model.binding)
    }

    override fun onDestroy() {
        super.onDestroy()
        model.binding.unbind()
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
    open fun provideStatusBarColor(): Int = android.R.color.white
}