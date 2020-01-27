package com.example.myjetpackapplication.business.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.example.myjetpackapplication.business.setting.databinding.ItemSingleSettingBinding
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter
import java.lang.ref.WeakReference

/**
 * Created by liutiantian on 2019-12-22 22:04 星期日
 */
class SingleSettingItemAdapter(
    context: Context,
    items: List<BusinessItem>?,
    itemClickListener: (BusinessItem?, Int) -> Unit
) : BasicRecyclerAdapter<BusinessItem, SingleSettingItemHolder>(context, items, itemClickListener) {
    override fun onCreateViewHolder(view: ViewGroup, itemType: Int): SingleSettingItemHolder =
        SingleSettingItemHolder(
            WeakReference(context),
            ItemSingleSettingBinding.inflate(LayoutInflater.from(context))
        )
}