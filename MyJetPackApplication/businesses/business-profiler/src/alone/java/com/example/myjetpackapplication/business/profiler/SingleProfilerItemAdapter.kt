package com.example.myjetpackapplication.business.profiler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.example.myjetpackapplication.business.profiler.databinding.ItemSingleProfilerBinding
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter

/**
 * Created by liutiantian on 2019-12-22 22:04 星期日
 */
class SingleProfilerItemAdapter(
    context: Context,
    items: List<BusinessItem>?,
    itemClickListener: (BusinessItem?, Int) -> Unit
) : BasicRecyclerAdapter<BusinessItem, SingleProfilerItemHolder, ItemSingleProfilerBinding>(
    context,
    items,
    itemClickListener
) {
    override fun onCreateViewHolder(view: ViewGroup, itemType: Int): SingleProfilerItemHolder =
        SingleProfilerItemHolder(
            context,
            ItemSingleProfilerBinding.inflate(LayoutInflater.from(context))
        )
}