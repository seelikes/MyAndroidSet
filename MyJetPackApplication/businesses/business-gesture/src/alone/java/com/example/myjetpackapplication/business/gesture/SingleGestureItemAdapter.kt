package com.example.myjetpackapplication.business.gesture

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.annotationprocessor.businessannotationprocessor.annotation.BusinessItem
import com.example.myjetpackapplication.business.gesture.databinding.ItemSingleGestureBinding
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter

/**
 * Created by liutiantian on 2019-12-22 22:04 星期日
 */
class SingleGestureItemAdapter(context: Context, items: List<BusinessItem>?, itemClickListener: (BusinessItem?, Int) -> Unit) : BasicRecyclerAdapter<BusinessItem, SingleGestureItemHolder, ItemSingleGestureBinding>(context, items, itemClickListener) {
    override fun onCreateViewHolder(view: ViewGroup, itemType: Int): SingleGestureItemHolder =
        SingleGestureItemHolder(
            context,
            ItemSingleGestureBinding.inflate(LayoutInflater.from(context))
        )
}