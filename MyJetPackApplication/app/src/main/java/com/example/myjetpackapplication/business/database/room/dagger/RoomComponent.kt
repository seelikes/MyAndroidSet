package com.example.myjetpackapplication.business.database.room.dagger

import com.example.myjetpackapplication.business.database.room.RoomActivity
import com.example.myjetpackapplication.business.database.room.RoomDataModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by liutiantian on 2019-05-09 14:46 星期四
 */
@Singleton
@Component(modules = [RoomModule::class])
interface RoomComponent {
    fun inject(subject: RoomActivity)

    fun inject(subject: RoomDataModel)
}