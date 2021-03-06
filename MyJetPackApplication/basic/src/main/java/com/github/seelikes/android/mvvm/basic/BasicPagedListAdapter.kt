package com.github.seelikes.android.mvvm.basic

import android.content.Context
import androidx.annotation.CallSuper
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.github.seelikes.android.mvvm.basic.utils.runIfClassHasAnnotation
import kotlinx.coroutines.CoroutineScope
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by liutiantian on 2019-05-09 09:24 星期四
 */
abstract class BasicPagedListAdapter<T : Any, VH : BasicViewHolder<T, *>, DC : DiffUtil.ItemCallback<T>> @JvmOverloads constructor(protected val context: Context, private val dc: DC, private val itemClickListener: ((T?, Int) -> Unit)? = null, protected val routineScope: CoroutineScope? = null) : PagedListAdapter<T, VH>(dc) {
    override fun onBindViewHolder(holder: VH, position: Int) {
        val t = getItem(position)
        holder.setData(t)
        itemClickListener?.apply {
            holder.clickListener = {
                invoke(it, position)
            }
        }
    }

    @CallSuper
    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        holder.runIfClassHasAnnotation(Subscribe::class.java) {
            if (!EventBus.getDefault().isRegistered(holder)) {
                EventBus.getDefault().register(holder)
            }
        }
    }

    @CallSuper
    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        if (EventBus.getDefault().isRegistered(holder)) {
            EventBus.getDefault().unregister(holder)
        }
    }
}