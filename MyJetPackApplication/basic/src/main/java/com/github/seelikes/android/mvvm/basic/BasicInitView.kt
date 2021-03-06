package com.github.seelikes.android.mvvm.basic

import androidx.databinding.ViewDataBinding

internal interface BasicInitView<B : ViewDataBinding> {
    /**
     * 初始化视图
     * 该方法在binding已经持有model之后调用
     * 子类不应该设法持有binding实例
     * 设计的初衷是为了让所有逻辑围绕数据运作
     */
    fun initView(binding: B) {

    }

    /**
     * 视图set到binding之前调用
     */
    fun beforeSetToBinding() {

    }

    /**
     * 视图set到binding之后调用
     */
    fun afterSetToBinding() {

    }
}