package com.example.myjetpackapplication.business.room

import android.content.Context
import androidx.room.Room
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by liutiantian on 2019-05-08 20:51 星期三
 */
@Module
class RoomModule(private val context: Context) {
    @Singleton
    @Provides
    fun provideRoomDatabase(): RoomDatabase {
        return Room.databaseBuilder(context.applicationContext, RoomDatabase::class.java, "room").build()
    }
}

@Singleton
@Component(modules = [RoomModule::class])
interface RoomComponent {
    fun inject(subject: RoomActivity)
}