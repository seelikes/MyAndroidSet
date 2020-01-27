package com.example.myjetpackapplication.business.database.realm.data

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by liutiantian on 2020-01-26 18:00 星期日
 */
class DatabaseRealmDiffUtil : DiffUtil.ItemCallback<DatabaseRealmBean>() {
    override fun areItemsTheSame(oldItem: DatabaseRealmBean, newItem: DatabaseRealmBean): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: DatabaseRealmBean, newItem: DatabaseRealmBean): Boolean = oldItem == newItem
}