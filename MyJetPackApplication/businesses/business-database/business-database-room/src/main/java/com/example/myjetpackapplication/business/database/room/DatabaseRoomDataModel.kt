package com.example.myjetpackapplication.business.database.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myjetpackapplication.business.database.room.dagger.DaggerDatabaseRoomComponent
import com.example.myjetpackapplication.business.database.room.dagger.DatabaseRoomModule
import com.example.myjetpackapplication.business.database.room.data.DatabaseRoomDatabase
import com.orhanobut.logger.Logger
import javax.inject.Inject

/**
 * Created by liutiantian on 2019-05-09 13:10 星期四
 */
class DatabaseRoomDataModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var database: DatabaseRoomDatabase

    init {
        DaggerDatabaseRoomComponent.builder().databaseRoomModule(DatabaseRoomModule(application)).build().inject(this)
        Logger.i("init, database.hashCode(): ${database.hashCode()}")
    }

    val list by lazy {
        with(LivePagedListBuilder(
            database.roomDao().query(),
            PagedList
                .Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(15)
                .setPrefetchDistance(30)
                .setPageSize(15)
                .build()
        )) {
            build()
        }
    }
}
