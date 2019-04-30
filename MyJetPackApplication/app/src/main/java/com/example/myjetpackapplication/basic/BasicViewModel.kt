package com.example.myjetpackapplication.basic

import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.example.myjetpackapplication.utils.callWithParameter

open class BasicViewModel<C : BasicViewModel<C, B>, B : ViewDataBinding>(internal val binding: B) : ViewModel() {
    init {
        Log.i("BasicViewModel", "init.UL1200LP.DI1211, enter")
        this.beforeSetToBinding()
        this.callWithParameter(binding)
        this.afterSetToBinding()
    }

    open fun beforeSetToBinding() {

    }

    open fun afterSetToBinding() {

    }
}