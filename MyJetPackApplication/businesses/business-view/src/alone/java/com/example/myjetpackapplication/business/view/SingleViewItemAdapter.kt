package com.example.myjetpackapplication.business.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.example.myjetpackapplication.business.view.databinding.ItemSingleViewBinding
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter
import java.lang.ref.WeakReference

/**
 * Created by liutiantian on 2019-12-22 22:04 星期日
 */
class SingleViewItemAdapter(
    context: Context,
    items: List<BusinessItem>?,
    itemClickListener: (BusinessItem?, Int) -> Unit
) : BasicRecyclerAdapter<BusinessItem, SingleViewItemHolder>(context, items, itemClickListener) {
    override fun onCreateViewHolder(view: ViewGroup, itemType: Int): SingleViewItemHolder =
        SingleViewItemHolder(
            WeakReference(context),
            ItemSingleViewBinding.inflate(LayoutInflater.from(context))
        )
}