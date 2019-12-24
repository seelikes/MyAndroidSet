package com.example.myjetpackapplication.business.database.room.dagger

import android.content.Context
import androidx.room.Room
import com.example.myjetpackapplication.business.database.room.data.DatabaseRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by liutiantian on 2019-05-09 14:46 星期四
 */
@Module
class DatabaseRoomModule(private val context: Context) {
    @Singleton
    @Provides
    fun provideRoomDatabase(): DatabaseRoomDatabase {
        return Room.databaseBuilder(context.applicationContext, DatabaseRoomDatabase::class.java, "room").build()
    }
}