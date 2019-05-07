package com.example.myjetpackapplication.basic

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.myjetpackapplication.utils.callWithParameter

/**
 * Created by liutiantian on 2019-04-30 19:24 星期二
 */
open class BasicViewHolder<T : Any, B : ViewDataBinding>(@Suppress("UNUSED_PARAMETER") context: Context?, binding: B) : RecyclerView.ViewHolder(binding.root) {
    @Suppress("MemberVisibilityCanBePrivate")
    protected lateinit var entity: T

    var clickListener: ((T) -> Unit)? = null

    init {
        this.beforeSetToBinding()
        this.callWithParameter(binding)
        binding.root.post{
            this.afterSetToBinding()
        }
    }

    open fun setData(entity: T) {
        this.entity = entity
    }

    open fun beforeSetToBinding() {

    }

    open fun afterSetToBinding() {

    }
}