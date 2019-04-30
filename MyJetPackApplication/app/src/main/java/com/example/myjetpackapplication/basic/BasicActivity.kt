package com.example.myjetpackapplication.basic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BasicActivity<C : BasicActivity<C, M, B>, M : BasicHostViewModel<M, C, B>, B : ViewDataBinding> : AppCompatActivity(), BasicInitView<B> {
    @Suppress("MemberVisibilityCanBePrivate")
    protected lateinit var model: M

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = initModel(savedInstanceState)
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
}