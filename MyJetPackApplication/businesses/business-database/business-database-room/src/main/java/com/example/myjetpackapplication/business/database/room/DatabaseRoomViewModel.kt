package com.example.myjetpackapplication.business.database.room

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModelProviders
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel
import com.example.myjetpackapplication.business.database.room.data.DatabaseRoomEntity
import com.example.myjetpackapplication.business.database.room.databinding.ActivityDatabaseRoomBinding
import com.orhanobut.logger.Logger
import kotlin.random.Random

/**
 * Created by liutiantian on 2019-05-08 00:11 星期三
 */
class DatabaseRoomViewModel(host: DatabaseRoomActivity, binding: ActivityDatabaseRoomBinding) : BasicHostViewModel<DatabaseRoomViewModel, DatabaseRoomActivity, ActivityDatabaseRoomBinding>(host, binding) {
    val title = ObservableInt(R.string.database_room_title)

    fun onUiClickAddOne() {
        Logger.i("onUiClickAddOne.UL1200LP.DI1211, enter")
        host.collapseFloatMenu()
        val room = DatabaseRoomEntity()
        room.dice = Random(System.currentTimeMillis()).nextInt()
        room.time = System.currentTimeMillis()
        ArchTaskExecutor.getInstance().executeOnDiskIO {
            ViewModelProviders.of(host).get(DatabaseRoomDataModel::class.java).database.roomDao().insert(room)
        }
    }

    fun onUiClickRandomGenerate() {
        host.collapseFloatMenu()
        val random = Random(System.currentTimeMillis())
        val rooms = MutableList(128) {
            val room = DatabaseRoomEntity()
            room.dice = random.nextInt()
            room.time = System.currentTimeMillis()
            room
        }
        ArchTaskExecutor.getInstance().executeOnDiskIO {
            ViewModelProviders.of(host).get(DatabaseRoomDataModel::class.java).database.roomDao().insert(*rooms.toTypedArray())
        }
    }

    fun onUiClickClear() {
        host.collapseFloatMenu()
        ArchTaskExecutor.getInstance().executeOnDiskIO {
            ViewModelProviders.of(host).get(DatabaseRoomDataModel::class.java).database.roomDao().delete()
        }
    }
}