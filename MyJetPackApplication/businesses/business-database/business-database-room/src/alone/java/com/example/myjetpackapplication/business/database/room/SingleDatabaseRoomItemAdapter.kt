package com.example.myjetpackapplication.business.database.room

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.example.myjetpackapplication.business.database.room.databinding.ItemSingleDatabaseRoomBinding
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter

/**
 * Created by liutiantian on 2019-12-22 22:04 星期日
 */
class SingleDatabaseRoomItemAdapter(
    context: Context,
    items: List<BusinessItem>?,
    itemClickListener: (BusinessItem?, Int) -> Unit
) : BasicRecyclerAdapter<BusinessItem, SingleDatabaseRoomItemHolder, ItemSingleDatabaseRoomBinding>(
    context,
    items,
    itemClickListener
) {
    override fun onCreateViewHolder(view: ViewGroup, itemType: Int): SingleDatabaseRoomItemHolder =
        SingleDatabaseRoomItemHolder(
            context,
            ItemSingleDatabaseRoomBinding.inflate(LayoutInflater.from(context))
        )
}