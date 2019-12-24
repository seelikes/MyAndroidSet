package com.example.myjetpackapplication.business.database.room.dagger

import com.example.myjetpackapplication.business.database.room.DatabaseRoomActivity
import com.example.myjetpackapplication.business.database.room.DatabaseRoomDataModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by liutiantian on 2019-05-09 14:46 星期四
 */
@Singleton
@Component(modules = [DatabaseRoomModule::class])
interface DatabaseRoomComponent {
    fun inject(subject: DatabaseRoomActivity)

    fun inject(subject: DatabaseRoomDataModel)
}