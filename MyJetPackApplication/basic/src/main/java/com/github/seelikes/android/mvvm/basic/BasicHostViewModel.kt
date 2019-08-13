package com.github.seelikes.android.mvvm.basic

import androidx.databinding.ViewDataBinding

open class BasicHostViewModel<C : BasicHostViewModel<C, H, B>, H, B : ViewDataBinding>(val host: H, binding: B) : BasicViewModel<C, B>(binding)