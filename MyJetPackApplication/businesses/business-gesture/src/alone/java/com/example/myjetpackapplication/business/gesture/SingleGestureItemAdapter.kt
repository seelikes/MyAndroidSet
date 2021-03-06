package com.example.myjetpackapplication.business.gesture

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.example.myjetpackapplication.business.gesture.databinding.ItemSingleGestureBinding
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter
import java.lang.ref.WeakReference

/**
 * Created by liutiantian on 2019-12-22 22:04 星期日
 */
class SingleGestureItemAdapter(context: Context, items: List<BusinessItem>?, itemClickListener: (BusinessItem?, Int) -> Unit) : BasicRecyclerAdapter<BusinessItem, SingleGestureItemHolder>(context, items, itemClickListener) {
    override fun onCreateViewHolder(view: ViewGroup, itemType: Int): SingleGestureItemHolder =
        SingleGestureItemHolder(
            WeakReference(context),
            ItemSingleGestureBinding.inflate(LayoutInflater.from(context))
        )
}