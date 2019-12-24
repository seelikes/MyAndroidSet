package com.example.myjetpackapplication.business.database

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.example.myjetpackapplication.business.database.databinding.ItemSingleDatabaseBinding
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter

/**
 * Created by liutiantian on 2019-12-22 22:04 星期日
 */
class SingleDatabaseItemAdapter(
    context: Context,
    items: List<BusinessItem>?,
    itemClickListener: (BusinessItem?, Int) -> Unit
) : BasicRecyclerAdapter<BusinessItem, SingleDatabaseItemHolder, ItemSingleDatabaseBinding>(
    context,
    items,
    itemClickListener
) {
    override fun onCreateViewHolder(view: ViewGroup, itemType: Int): SingleDatabaseItemHolder =
        SingleDatabaseItemHolder(
            context,
            ItemSingleDatabaseBinding.inflate(LayoutInflater.from(context))
        )
}