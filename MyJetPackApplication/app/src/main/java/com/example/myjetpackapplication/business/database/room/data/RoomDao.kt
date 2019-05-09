package com.example.myjetpackapplication.business.database.room.data

import androidx.paging.DataSource
import androidx.room.*

/**
 * Created by liutiantian on 2019-05-08 19:48 星期三
 */
@Dao
interface RoomDao {
    @Query("SELECT * FROM room_entity")
    fun query(): DataSource.Factory<Int, RoomEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(room: RoomEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg room: RoomEntity): List<Long>

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(vararg room: RoomEntity): Int

    @Delete
    fun delete(vararg room: RoomEntity): Int
}