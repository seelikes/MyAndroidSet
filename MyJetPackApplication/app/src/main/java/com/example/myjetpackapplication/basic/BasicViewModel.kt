package com.example.myjetpackapplication.basic

import androidx.databinding.ViewDataBinding
import com.example.myjetpackapplication.utils.callWithParameter

open class BasicViewModel<C : BasicViewModel<C, B>, B : ViewDataBinding>(internal val binding: B) {
    init {
        this.beforeSetToBinding()
        this.callWithParameter(binding)
        this.afterSetToBinding()
    }

    open fun beforeSetToBinding() {

    }

    open fun afterSetToBinding() {

    }
}