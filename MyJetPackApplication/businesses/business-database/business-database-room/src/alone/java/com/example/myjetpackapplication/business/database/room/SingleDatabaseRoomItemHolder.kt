package com.example.myjetpackapplication.business.database.room

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.example.myjetpackapplication.business.database.room.databinding.ItemSingleDatabaseRoomBinding
import com.github.seelikes.android.mvvm.basic.BasicViewHolder

/**
 * Created by liutiantian on 2019-12-22 22:05 星期日
 */
class SingleDatabaseRoomItemHolder(context: Context, binding: ItemSingleDatabaseRoomBinding) : BasicViewHolder<BusinessItem, ItemSingleDatabaseRoomBinding>(context, binding) {
    val title = ObservableInt()
    val hasChildren = ObservableBoolean()

    override fun setData(entity: BusinessItem?) {
        super.setData(entity)
        hasChildren.set(BusinessManager.getChildren(entity).isNotEmpty())
        title.set(
            context?.resources?.getIdentifier(entity?.title, "string", context.packageName)
                ?: R.string.app_name
        )
    }

    fun onUiClick() {
        clickListener?.apply {
            invoke(entity)
        }
    }
}