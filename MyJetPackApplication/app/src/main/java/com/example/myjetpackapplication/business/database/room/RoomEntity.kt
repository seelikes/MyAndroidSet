package com.example.myjetpackapplication.business.database.room

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
    var id : Int = 0

    @ColumnInfo(name = "room_dice")
    var dice : Int = 0

    @ColumnInfo(name = "room_time")
    var time: Long = 0

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + dice
        result = 31 * result + time.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RoomEntity

        if (id != other.id) return false
        if (dice != other.dice) return false
        if (time != other.time) return false

        return true
    }
}