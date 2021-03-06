package com.example.myjetpackapplication.business.database.realm.data

import androidx.paging.ItemKeyedDataSource
import com.github.seelikes.android.cache.Cache
import com.github.seelikes.android.realm.api.realm
import com.orhanobut.logger.Logger
import io.realm.Sort
import io.realm.kotlin.where

val KEY_MAX_ID = Cache.generateRandomKey(19)
val KEY_SIZE = Cache.generateRandomKey(20)

/**
 * Created by liutiantian on 2020-01-26 18:07 星期日
 */
class DatabaseRealmDataSource(private val pageSize: Int = 15) : ItemKeyedDataSource<Long, DatabaseRealmBean>() {
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<DatabaseRealmBean>) {
        Logger.i("202001271322, params.requestedLoadSize: ${params.requestedLoadSize}; params.requestedInitialKey: ${params.requestedInitialKey}")
        Logger.i("202001271322, thread.name: ${Thread.currentThread().name}")
        realm.use {
            var requestedLoadSize: Long = Cache.get(KEY_SIZE, (params.requestedLoadSize / pageSize + (if (params.requestedLoadSize % pageSize != 0) 1 else 0)).toLong() * pageSize + 1)!!
            if (requestedLoadSize < pageSize) {
                requestedLoadSize = pageSize.toLong()
            }
            val maxId = Cache.get(KEY_MAX_ID, realm.where<DatabaseRealmEntity>().max("id") as? Long ?: 1)!!
            val count = realm.where<DatabaseRealmEntity>().count().toInt()
            Logger.i("202001271322, maxId: $maxId; count: $count")
            val list = realm.where<DatabaseRealmEntity>().lessThan("id", maxId + 1L).sort("createTime", Sort.DESCENDING).limit(requestedLoadSize).findAll().toList()

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
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<DatabaseRealmBean>) {
        realm.use {
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
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<DatabaseRealmBean>) {
        realm.use {
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
    }

    override fun getKey(item: DatabaseRealmBean): Long {
        return item.id!!
    }
}