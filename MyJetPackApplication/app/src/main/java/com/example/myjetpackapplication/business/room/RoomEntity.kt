package com.example.myjetpackapplication.business.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by liutiantian on 2019-05-08 19:35 星期三
 */
@Entity(tableName = "room_entity", indices = [Index("room_dice")])
class RoomEntity : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "room_id")
    var id : Long = 0

    @ColumnInfo(name = "room_dice")
    var dice : Int = 0

    @ColumnInfo(name = "room_time")
    var time: Long = 0
}