package com.example.myjetpackapplication.basic

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by liutiantian on 2019-04-30 19:43 星期二
 */
abstract class BasicRecyclerAdapter<T : Any, VH: BasicViewHolder<T, B>, B : ViewDataBinding>(@Suppress("unused") protected val context: Context, @Suppress("MemberVisibilityCanBePrivate") protected var items: List<T>? = null, private val itemClickListener: ((T, Int) -> Unit)? = null) : RecyclerView.Adapter<VH>() {
    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.setData(items!![position])
        itemClickListener?.apply {
            holder.clickListener = {
                invoke(it, position)
            }
        }
    }
}