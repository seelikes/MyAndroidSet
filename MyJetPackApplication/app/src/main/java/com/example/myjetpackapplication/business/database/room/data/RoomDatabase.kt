package com.example.myjetpackapplication.business.database.room.data

import androidx.room.Database

/**
 * Created by liutiantian on 2019-05-08 19:56 星期三
 */
@Database(entities = [RoomEntity::class], version = 1)
abstract class RoomDatabase : androidx.room.RoomDatabase() {
    abstract fun roomDao(): RoomDao
}