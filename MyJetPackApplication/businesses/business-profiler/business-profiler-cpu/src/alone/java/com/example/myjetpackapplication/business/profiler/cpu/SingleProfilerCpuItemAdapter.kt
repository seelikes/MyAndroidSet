package com.example.myjetpackapplication.business.profiler.cpu

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.example.myjetpackapplication.business.profiler.cpu.databinding.ItemSingleProfilerCpuBinding
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter

/**
 * Created by liutiantian on 2019-12-22 22:04 星期日
 */
class SingleProfilerCpuItemAdapter(
    context: Context,
    items: List<BusinessItem>?,
    itemClickListener: (BusinessItem?, Int) -> Unit
) : BasicRecyclerAdapter<BusinessItem, SingleProfilerCpuItemHolder, ItemSingleProfilerCpuBinding>(
    context,
    items,
    itemClickListener
) {
    override fun onCreateViewHolder(view: ViewGroup, itemType: Int): SingleProfilerCpuItemHolder =
        SingleProfilerCpuItemHolder(
            context,
            ItemSingleProfilerCpuBinding.inflate(LayoutInflater.from(context))
        )
}