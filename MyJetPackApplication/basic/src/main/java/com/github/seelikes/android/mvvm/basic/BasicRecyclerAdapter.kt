package com.github.seelikes.android.mvvm.basic

import android.content.Context
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.github.seelikes.android.mvvm.basic.utils.runIfClassHasAnnotation
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by liutiantian on 2019-04-30 19:43 星期二
 */
abstract class BasicRecyclerAdapter<T : Any, VH: BasicViewHolder<T, B>, B : ViewDataBinding>(@Suppress("unused") protected val context: Context, @Suppress("MemberVisibilityCanBePrivate") protected var items: List<T>? = null, private val itemClickListener: ((T?, Int) -> Unit)? = null) : RecyclerView.Adapter<VH>() {
    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.setData(items?.get(position))
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
        holder.runIfClassHasAnnotation(Subscribe::class.java) {
            if (EventBus.getDefault().isRegistered(holder)) {
                EventBus.getDefault().unregister(holder)
            }
        }
    }
}