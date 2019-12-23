package com.example.myjetpackapplication.business.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter
import com.example.myjetpackapplication.databinding.ItemMainBinding

/**
 * Created by liutiantian on 2019-05-06 23:20 星期一
 */
class MainItemAdapter(context: Context, items: List<BusinessItem>?, itemClickListener: (BusinessItem?, Int) -> Unit) : BasicRecyclerAdapter<BusinessItem, MainItemViewHolder, ItemMainBinding>(context, items, itemClickListener) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemViewHolder = MainItemViewHolder(context, ItemMainBinding.inflate(LayoutInflater.from(context), parent, false))
}