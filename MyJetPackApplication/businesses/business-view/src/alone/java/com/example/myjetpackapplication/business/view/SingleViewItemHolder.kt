package com.example.myjetpackapplication.business.view

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.example.myjetpackapplication.annotationprocessor.business.api.BusinessApi
import com.example.myjetpackapplication.business.view.databinding.ItemSingleViewBinding
import com.github.seelikes.android.mvvm.basic.BasicViewHolder
import com.github.seelikes.android.mvvm.basic.context
import com.orhanobut.logger.Logger
import java.lang.ref.WeakReference

/**
 * Created by liutiantian on 2019-12-22 22:05 星期日
 */
class SingleViewItemHolder(weakContext: WeakReference<Context>, binding: ItemSingleViewBinding) :
    BasicViewHolder<BusinessItem, ItemSingleViewBinding>(weakContext, binding) {
    val title = ObservableInt()
    val hasChildren = ObservableBoolean()

    override fun setData(entity: BusinessItem?) {
        super.setData(entity)
        hasChildren.set(!BusinessApi.getChildren(entity).isNullOrEmpty())
        title.set(
            context?.resources?.getIdentifier(entity?.title, "string", context?.packageName)
                ?: R.string.app_name
        )
    }

    fun onUiClick() {
        clickListener?.apply {
            invoke(entity)
        }
    }
}