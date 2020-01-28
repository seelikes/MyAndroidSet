package com.example.myjetpackapplication.business.database.realm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myjetpackapplication.business.database.realm.data.*
import com.github.seelikes.android.cache.Cache
import com.github.seelikes.android.realm.api.realm
import com.orhanobut.logger.Logger
import io.realm.Sort
import io.realm.kotlin.where
import java.util.*

class DatabaseRealmDataModel(application: Application) : AndroidViewModel(application) {
    val list by lazy {
        LivePagedListBuilder(
            object : DataSource.Factory<Long, DatabaseRealmBean>() {
                override fun create(): DataSource<Long, DatabaseRealmBean> {
                    return DatabaseRealmDataSource(15)
                }
            },
            PagedList.Config.Builder()
                .setPrefetchDistance(30)
                .setPageSize(15)
                .setInitialLoadSizeHint(30)
                .setEnablePlaceholders(false)
                .build()
        ).build()
    }

    fun add(realmBean: DatabaseRealmBean, close: Boolean? = false) {
        try {
            Logger.i("202001271811, realmBean: $realmBean")
            val realmEntity = DatabaseRealmEntity()
            realmEntity.id = (realm.where<DatabaseRealmEntity>().max("id")?.toLong() ?: -1) + 1
            realmEntity.key = realmBean.key
            realmEntity.value = realmBean.value
            realmEntity.createTime = Date()
            realm.beginTransaction()
            realm.copyToRealm(realmEntity)
            realm.commitTransaction()
        }
        finally {
            if (close == true) {
                realm.close()
            }
            rememberMe()
            list.value?.dataSource?.invalidate()
        }
    }

    fun delete(realmEntity: DatabaseRealmBean? = null, close: Boolean? = false) {
        try {
            realmEntity?.id?.let {
                val entity: DatabaseRealmEntity? = realm.where<DatabaseRealmEntity>(DatabaseRealmEntity::class.java).equalTo("id", it).findFirst()
                realm.beginTransaction()
                entity?.deleteFromRealm()
                realm.commitTransaction()
                rememberMe()
                list.value?.dataSource?.invalidate()
                return
            }
            realm.executeTransaction {
                realm.delete(DatabaseRealmEntity::class.java)
                forgetMe()
            }
            list.value?.dataSource?.invalidate()
        }
        finally {
            if (close == true) {
                realm.close()
            }
        }
    }

    private fun forgetMe() {
        Cache.remove(KEY_SIZE)
        Cache.remove(KEY_MAX_ID)
    }

    private fun rememberMe() {
        Cache.strong(KEY_SIZE, list.value?.snapshot()?.size)
        val max = list.value?.snapshot()?.maxWith(kotlin.Comparator<DatabaseRealmBean> { o1, o2 -> ((o1?.id ?: 0) - (o2?.id ?: 0)).toInt() })
        Cache.strong(KEY_MAX_ID, max?.id)
    }
}