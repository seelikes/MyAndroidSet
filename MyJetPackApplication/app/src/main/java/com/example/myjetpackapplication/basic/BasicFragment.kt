package com.example.myjetpackapplication.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BasicFragment<C : BasicFragment<C, M, B>, M : BasicHostViewModel<M, C, B>, B : ViewDataBinding> : Fragment(), BasicInitView<B> {
    @Suppress("MemberVisibilityCanBePrivate")
    protected lateinit var model: M

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        model = initModel(inflater, container, savedInstanceState)
        initView(model.binding)
        return model.binding.root
    }

    /**
     * 初始化model
     */
    abstract fun initModel(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): M
}