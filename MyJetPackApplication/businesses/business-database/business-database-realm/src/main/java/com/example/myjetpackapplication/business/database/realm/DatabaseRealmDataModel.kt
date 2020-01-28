package com.example.myjetpackapplication.business.database.realm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myjetpackapplication.business.database.realm.data.DatabaseRealmBean
import com.example.myjetpackapplication.business.database.realm.data.DatabaseRealmDataSource
import com.example.myjetpackapplication.business.database.realm.data.DatabaseRealmEntity
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
                list.value?.dataSource?.invalidate()
                return
            }
            realm.executeTransaction {
                realm.delete(DatabaseRealmEntity::class.java)
            }
            list.value?.dataSource?.invalidate()
        }
        finally {
            if (close == true) {
                realm.close()
            }
        }
    }
}