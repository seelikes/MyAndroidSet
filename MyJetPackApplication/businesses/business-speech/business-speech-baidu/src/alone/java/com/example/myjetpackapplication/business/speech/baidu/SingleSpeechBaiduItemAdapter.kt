package com.example.myjetpackapplication.business.speech.baidu

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.example.myjetpackapplication.business.speech.baidu.databinding.ItemSingleSpeechBaiduBinding
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter
import java.lang.ref.WeakReference

/**
 * Created by liutiantian on 2019-12-22 22:04 星期日
 */
class SingleSpeechBaiduItemAdapter(
    context: Context,
    items: List<BusinessItem>?,
    itemClickListener: (BusinessItem?, Int) -> Unit
) : BasicRecyclerAdapter<BusinessItem, SingleSpeechBaiduItemHolder>(
    context,
    items,
    itemClickListener
) {
    override fun onCreateViewHolder(view: ViewGroup, itemType: Int): SingleSpeechBaiduItemHolder =
        SingleSpeechBaiduItemHolder(
            WeakReference(context),
            ItemSingleSpeechBaiduBinding.inflate(LayoutInflater.from(context))
        )
}