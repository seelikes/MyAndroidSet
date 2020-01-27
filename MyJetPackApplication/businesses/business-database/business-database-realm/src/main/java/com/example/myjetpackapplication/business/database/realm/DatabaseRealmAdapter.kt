package com.example.myjetpackapplication.business.database.realm

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.business.database.realm.data.DatabaseRealmBean
import com.example.myjetpackapplication.business.database.realm.data.DatabaseRealmDiffUtil
import com.example.myjetpackapplication.business.database.realm.data.DatabaseRealmEntity
import com.example.myjetpackapplication.business.database.realm.databinding.ItemDatabaseRealmBinding
import com.github.seelikes.android.mvvm.basic.BasicPagedListAdapter
import kotlinx.coroutines.CoroutineScope

/**
 * Created by liutiantian on 2020-01-26 17:46 星期日
 */
class DatabaseRealmAdapter(context: Context, routineScope: CoroutineScope) : BasicPagedListAdapter<DatabaseRealmBean, DatabaseRealmViewHolder, DatabaseRealmDiffUtil>(context, DatabaseRealmDiffUtil(), null, routineScope) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatabaseRealmViewHolder = DatabaseRealmViewHolder(context, ItemDatabaseRealmBinding.inflate(LayoutInflater.from(context), parent, false), routineScope)
}