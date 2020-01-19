package com.example.myjetpackapplication.business.speech

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.example.myjetpackapplication.business.speech.databinding.ItemSingleSpeechBinding
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter
import java.lang.ref.WeakReference

/**
 * Created by liutiantian on 2019-12-22 22:04 星期日
 */
class SingleSpeechItemAdapter(
    context: Context,
    items: List<BusinessItem>?,
    itemClickListener: (BusinessItem?, Int) -> Unit
) : BasicRecyclerAdapter<BusinessItem, SingleSpeechItemHolder, ItemSingleSpeechBinding>(
    context,
    items,
    itemClickListener
) {
    override fun onCreateViewHolder(view: ViewGroup, itemType: Int): SingleSpeechItemHolder =
        SingleSpeechItemHolder(
            WeakReference(context),
            ItemSingleSpeechBinding.inflate(LayoutInflater.from(context))
        )
}