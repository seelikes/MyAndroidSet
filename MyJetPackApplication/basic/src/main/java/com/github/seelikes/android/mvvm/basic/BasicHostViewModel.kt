package com.github.seelikes.android.mvvm.basic

import androidx.databinding.ViewDataBinding
import com.github.seelikes.android.mvvm.basic.utils.callWithParameter
import kotlinx.coroutines.CoroutineScope

open class BasicHostViewModel<C : BasicHostViewModel<C, H, B>, H: Any, B : ViewDataBinding> @JvmOverloads constructor(val host: H, binding: B, routineScope: CoroutineScope? = null) : BasicViewModel<C, B>(binding, routineScope) {
    init {
        if (host is BasicInitView<*>) {
            host.beforeSetToBinding()
        }
        host.callWithParameter(binding)
        if (host is BasicInitView<*>) {
            binding.root.post {
                host.afterSetToBinding()
            }
        }
    }
}