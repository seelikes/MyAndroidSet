package com.example.myjetpackapplication.business.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.example.myjetpackapplication.business.paging.databinding.ItemSinglePagingBinding
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter
import java.lang.ref.WeakReference

/**
 * Created by liutiantian on 2019-12-22 22:04 星期日
 */
class SinglePagingItemAdapter(
    context: Context,
    items: List<BusinessItem>?,
    itemClickListener: (BusinessItem?, Int) -> Unit
) : BasicRecyclerAdapter<BusinessItem, SinglePagingItemHolder>(
    context,
    items,
    itemClickListener
) {
    override fun onCreateViewHolder(view: ViewGroup, itemType: Int): SinglePagingItemHolder =
        SinglePagingItemHolder(
            WeakReference(context),
            ItemSinglePagingBinding.inflate(LayoutInflater.from(context))
        )
}