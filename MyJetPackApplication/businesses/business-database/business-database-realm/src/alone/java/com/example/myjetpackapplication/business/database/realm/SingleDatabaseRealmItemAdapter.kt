package com.example.myjetpackapplication.business.database.realm

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.example.myjetpackapplication.business.database.realm.databinding.ItemSingleDatabaseRealmBinding
import com.github.seelikes.android.mvvm.basic.BasicRecyclerAdapter
import java.lang.ref.WeakReference

/**
 * Created by liutiantian on 2019-12-22 22:04 星期日
 */
class SingleDatabaseRealmItemAdapter(
    context: Context,
    items: List<BusinessItem>?,
    itemClickListener: (BusinessItem?, Int) -> Unit
) : BasicRecyclerAdapter<BusinessItem, SingleDatabaseRealmItemHolder>(
    context,
    items,
    itemClickListener
) {
    override fun onCreateViewHolder(view: ViewGroup, itemType: Int): SingleDatabaseRealmItemHolder =
        SingleDatabaseRealmItemHolder(
            WeakReference(context),
            ItemSingleDatabaseRealmBinding.inflate(LayoutInflater.from(context))
        )
}