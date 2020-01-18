package com.github.seelikes.android.mvvm.basic

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.github.seelikes.android.mvvm.basic.utils.callWithParameter

/**
 * Created by liutiantian on 2019-04-30 19:24 星期二
 */
open class BasicViewHolder<T : Any, B : ViewDataBinding>(protected val context: Context?, protected val binding: B) : RecyclerView.ViewHolder(binding.root) {
    @Suppress("MemberVisibilityCanBePrivate")
    protected var entity: T? = null

    var clickListener: ((T?) -> Unit)? = null

    init {
        this.beforeSetToBinding()
        this.callWithParameter(binding)
        binding.root.setOnClickListener {
            clickListener?.invoke(entity)
        }
        binding.root.post{
            this.afterSetToBinding()
        }
    }

    open fun setData(entity: T?) {
        this.entity = entity
    }

    open fun beforeSetToBinding() {

    }

    open fun afterSetToBinding() {

    }
}