package com.example.myjetpackapplication.business.database.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myjetpackapplication.business.database.room.dagger.DaggerRoomComponent
import com.example.myjetpackapplication.business.database.room.dagger.RoomModule
import javax.inject.Inject

/**
 * Created by liutiantian on 2019-05-09 13:10 星期四
 */
class RoomDataModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var database: RoomDatabase

    init {
        DaggerRoomComponent.builder().roomModule(RoomModule(application)).build().inject(this)
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
