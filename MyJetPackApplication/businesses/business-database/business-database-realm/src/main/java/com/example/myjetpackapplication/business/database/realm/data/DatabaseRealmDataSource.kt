package com.example.myjetpackapplication.business.database.realm.data

import androidx.paging.ItemKeyedDataSource
import com.github.seelikes.android.realm.api.realm
import com.orhanobut.logger.Logger
import io.realm.Sort
import io.realm.kotlin.where

/**
 * Created by liutiantian on 2020-01-26 18:07 星期日
 */
class DatabaseRealmDataSource(private val pageSize: Int = 15) : ItemKeyedDataSource<Long, DatabaseRealmBean>() {
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<DatabaseRealmBean>) {
        Logger.i("202001271322, thread.name: ${Thread.currentThread().name}")
        val nextKey: Long = (params.requestedLoadSize / pageSize + (if (params.requestedLoadSize % pageSize != 0) 1 else 0)).toLong()
        val maxId = realm.where<DatabaseRealmEntity>().max("id") as? Long ?: 0
        val count = realm.where<DatabaseRealmEntity>().count().toInt()
        Logger.i("202001271322, maxId: $maxId; count: $count")
        val list = realm.where<DatabaseRealmEntity>().lessThan("id", maxId + 1L).sort("createTime", Sort.DESCENDING).limit(pageSize * nextKey).findAll().toList()
        Logger.i("202001271322, list: ${list.map { it.key }}")
        val map = list.map {
            val bean = DatabaseRealmBean()
            bean.id = it.id
            bean.key = it.key
            bean.value = it.value
            bean.createTime = it.createTime
            bean.updateTime = it.updateTime
            bean
        }
        callback.onResult(map.toMutableList(), 0, realm.where<DatabaseRealmEntity>().lessThan("id", maxId + 1L).count().toInt())
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<DatabaseRealmBean>) {
        val list = realm.where<DatabaseRealmEntity>().lessThan("id", params.key).sort("createTime", Sort.DESCENDING).limit(pageSize.toLong()).findAll().toList()
        Logger.i("params.key: ${params.key }}; list.size: ${list.size}")
        val map = list.map {
            val bean = DatabaseRealmBean()
            bean.id = it.id
            bean.key = it.key
            bean.value = it.value
            bean.createTime = it.createTime
            bean.updateTime = it.updateTime
            bean
        }
        callback.onResult(map.toMutableList())
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<DatabaseRealmBean>) {
        val list = realm.where<DatabaseRealmEntity>().greaterThan("id", params.key).sort("createTime", Sort.ASCENDING).limit(pageSize.toLong()).findAll().toList().reversed()
        Logger.i("params.key: ${params.key }}; list.size: ${list.size}")
        val map = list.map {
            val bean = DatabaseRealmBean()
            bean.id = it.id
            bean.key = it.key
            bean.value = it.value
            bean.createTime = it.createTime
            bean.updateTime = it.updateTime
            bean
        }
        callback.onResult(map.toMutableList())
    }

    override fun getKey(item: DatabaseRealmBean): Long {
        return item.id!!
    }
}