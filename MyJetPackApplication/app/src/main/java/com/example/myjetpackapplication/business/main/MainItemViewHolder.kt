package com.example.myjetpackapplication.business.main

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.basic.BasicViewHolder
import com.example.myjetpackapplication.databinding.ItemMainBinding

/**
 * Created by liutiantian on 2019-05-05 13:23 星期日
 */
class MainItemViewHolder(context: Context, binding: ItemMainBinding) : BasicViewHolder<MainItemBean, ItemMainBinding>(context, binding) {
    var title = ObservableInt()
    var hasChildren = ObservableBoolean()

    override fun setData(entity: MainItemBean?) {
        super.setData(entity)
        hasChildren.set(entity?.children?.isNotEmpty() ?: false)
        title.set(entity?.title ?: 0)
    }

    fun onUiClick() {
        clickListener?.apply {
            invoke(entity)
        }
    }
}
