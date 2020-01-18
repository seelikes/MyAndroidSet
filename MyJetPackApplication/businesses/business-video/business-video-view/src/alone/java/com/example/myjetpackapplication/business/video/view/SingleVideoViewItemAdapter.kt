package com.example.myjetpackapplication.business.video.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.example.myjetpackapplication.business.video.view.databinding.ItemSingleVideoViewBinding
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter

/**
 * Created by liutiantian on 2019-12-22 22:04 星期日
 */
class SingleVideoViewItemAdapter(
    context: Context,
    items: List<BusinessItem>?,
    itemClickListener: (BusinessItem?, Int) -> Unit
) : BasicRecyclerAdapter<BusinessItem, SingleVideoViewItemHolder, ItemSingleVideoViewBinding>(
    context,
    items,
    itemClickListener
) {
    override fun onCreateViewHolder(view: ViewGroup, itemType: Int): SingleVideoViewItemHolder =
        SingleVideoViewItemHolder(
            context,
            ItemSingleVideoViewBinding.inflate(LayoutInflater.from(context))
        )
}