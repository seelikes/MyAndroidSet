package com.github.seelikes.android.mvvm.basic

import androidx.databinding.ViewDataBinding
import com.github.seelikes.android.mvvm.basic.utils.callWithParameter

open class BasicHostViewModel<C : BasicHostViewModel<C, H, B>, H: Any, B : ViewDataBinding>(val host: H, binding: B) : BasicViewModel<C, B>(binding) {
    init {
        if (host is BasicInitView<*>) {
            host.beforeSetToBinding()
        }
        this.callWithParameter(host)
        if (host is BasicInitView<*>) {
            binding.root.post {
                host.afterSetToBinding()
            }
        }
    }
}