package com.example.myjetpackapplication.business.database.room

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModelProviders
import com.example.myjetpackapplication.R
import com.example.myjetpackapplication.basic.BasicHostViewModel
import com.example.myjetpackapplication.business.database.room.data.RoomEntity
import com.example.myjetpackapplication.databinding.ActivityRoomBinding
import com.orhanobut.logger.Logger
import kotlin.random.Random

/**
 * Created by liutiantian on 2019-05-08 00:11 星期三
 */
class RoomViewModel(host: RoomActivity, binding: ActivityRoomBinding) : BasicHostViewModel<RoomViewModel, RoomActivity, ActivityRoomBinding>(host, binding) {
    val title = ObservableInt(R.string.room_title)

    fun onUiClickAddOne() {
        Logger.i("onUiClickAddOne.UL1200LP.DI1211, enter")
        host.collapseFloatMenu()
        val room = RoomEntity()
        room.dice = Random(System.currentTimeMillis()).nextInt()
        room.time = System.currentTimeMillis()
        ArchTaskExecutor.getInstance().executeOnDiskIO {
            ViewModelProviders.of(host).get(RoomDataModel::class.java).database.roomDao().insert(room)
        }
    }

    fun onUiClickRandomGenerate() {
        host.collapseFloatMenu()
        val random = Random(System.currentTimeMillis())
        val rooms = MutableList(128) {
            val room = RoomEntity()
            room.dice = random.nextInt()
            room.time = System.currentTimeMillis()
            room
        }
        ArchTaskExecutor.getInstance().executeOnDiskIO {
            ViewModelProviders.of(host).get(RoomDataModel::class.java).database.roomDao().insert(*rooms.toTypedArray())
        }
    }

    fun onUiClickClear() {
        host.collapseFloatMenu()
        ArchTaskExecutor.getInstance().executeOnDiskIO {
            ViewModelProviders.of(host).get(RoomDataModel::class.java).database.roomDao().delete()
        }
    }
}