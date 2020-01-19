package com.example.myjetpackapplication.business.main

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.example.myjetpackapplication.annotationprocessor.business.api.BusinessApi
import com.example.myjetpackapplication.databinding.ItemMainBinding
import com.github.seelikes.android.mvvm.basic.*
import java.lang.ref.WeakReference

/**
 * Created by liutiantian on 2019-05-05 13:23 星期日
 */
class MainItemViewHolder(weakContext: WeakReference<Context>, binding: ItemMainBinding) : BasicViewHolder<BusinessItem, ItemMainBinding>(weakContext, binding) {
    var title = ObservableInt()
    var hasChildren = ObservableBoolean()

    override fun setData(entity: BusinessItem?) {
        super.setData(entity)
        val children = BusinessApi.getChildren(entity)
        hasChildren.set(children != null && children.isNotEmpty())
        title.set(context?.resources?.getIdentifier(entity?.title, "string", context?.packageName) ?: 0)
    }

    fun onUiClick() {
        clickListener?.apply {
            invoke(entity)
        }
    }
}
