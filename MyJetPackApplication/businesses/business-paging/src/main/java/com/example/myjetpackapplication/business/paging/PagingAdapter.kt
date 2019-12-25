package com.example.myjetpackapplication.business.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.github.seelikes.android.mvvm.basic.BasicPagedListAdapter
import com.example.myjetpackapplication.business.paging.data.PagingEntity
import com.example.myjetpackapplication.business.paging.databinding.ItemPagingBinding

/**
 * Created by liutiantian on 2019-05-14 13:39 星期二
 */

class PagingDiffUtil : DiffUtil.ItemCallback<PagingEntity>() {
    override fun areItemsTheSame(oldItem: PagingEntity, newItem: PagingEntity): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PagingEntity, newItem: PagingEntity): Boolean = oldItem == newItem
}

class PagingAdapter(context: Context, dc: PagingDiffUtil = PagingDiffUtil()) : BasicPagedListAdapter<PagingEntity, PagingViewHolder, PagingDiffUtil, ItemPagingBinding>(context, dc) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder = PagingViewHolder(context, ItemPagingBinding.inflate(LayoutInflater.from(context), parent, false))
}