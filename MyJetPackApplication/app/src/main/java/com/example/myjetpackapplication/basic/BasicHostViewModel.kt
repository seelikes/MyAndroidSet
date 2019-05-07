package com.example.myjetpackapplication.basic

import androidx.databinding.ViewDataBinding

open class BasicHostViewModel<C : BasicHostViewModel<C, H, B>, H, B : ViewDataBinding>(protected val host: H, binding: B) : BasicViewModel<C, B>(binding)