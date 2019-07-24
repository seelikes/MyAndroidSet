package com.example.myjetpackapplication.business.paging

import android.content.Context
import androidx.databinding.ObservableField
import com.github.seelikes.android.mvvm.basic.BasicViewHolder
import com.example.myjetpackapplication.business.paging.data.PagingEntity
import com.example.myjetpackapplication.databinding.ItemPagingBinding
import com.example.myjetpackapplication.utils.format
import java.util.*

/**
 * Created by liutiantian on 2019-05-14 13:43 星期二
 */
class PagingViewHolder(context: Context, binding: ItemPagingBinding) : BasicViewHolder<PagingEntity, ItemPagingBinding>(context, binding) {
    val title = ObservableField<String>()
    val paging = ObservableField<String>()
    val time = ObservableField<String>()

    override fun setData(entity: PagingEntity?) {
        super.setData(entity)
        title.set(entity?.id.toString())
        paging.set(entity?.paging ?: "")
        time.set(entity?.time?.let { format(Date(it)) } ?: "")
    }
}