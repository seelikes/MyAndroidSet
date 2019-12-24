package com.example.myjetpackapplication.business.database.room.data

import androidx.paging.DataSource
import androidx.room.*

/**
 * Created by liutiantian on 2019-05-08 19:48 星期三
 */
@Dao
interface DatabaseRoomDao {
    @Query("SELECT * FROM room_entity")
    fun query(): DataSource.Factory<Int, DatabaseRoomEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(room: DatabaseRoomEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg room: DatabaseRoomEntity): List<Long>

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(vararg room: DatabaseRoomEntity): Int

    @Delete
    fun delete(vararg room: DatabaseRoomEntity): Int

    @Query("DELETE FROM room_entity")
    fun delete(): Int
}