package com.github.seelikes.android.mvvm.basic

import androidx.databinding.ViewDataBinding
import com.github.seelikes.android.mvvm.basic.utils.callWithParameter

open class BasicViewModel<C : BasicViewModel<C, B>, B : ViewDataBinding>(internal val binding: B) {
    init {
        this.beforeSetToBinding()
        this.callWithParameter(binding)
        binding.root.post {
            this.afterSetToBinding()
        }
    }

    open fun beforeSetToBinding() {

    }

    open fun afterSetToBinding() {

    }
}