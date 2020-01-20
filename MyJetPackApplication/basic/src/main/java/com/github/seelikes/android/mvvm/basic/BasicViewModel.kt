package com.github.seelikes.android.mvvm.basic

import androidx.databinding.ViewDataBinding
import com.github.seelikes.android.mvvm.basic.utils.callWithParameter
import kotlinx.coroutines.CoroutineScope

open class BasicViewModel<C : BasicViewModel<C, B>, B : ViewDataBinding> @JvmOverloads constructor(internal val binding: B, protected val routineScope: CoroutineScope? = null) {
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