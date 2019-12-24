package com.example.myjetpackapplication.business.database.room

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.github.seelikes.android.mvvm.basic.BasicPagedListAdapter
import com.example.myjetpackapplication.business.database.room.data.DatabaseRoomEntity
import com.example.myjetpackapplication.business.database.room.databinding.ItemDatabaseRoomBinding

/**
 * Created by liutiantian on 2019-05-09 13:36 星期四
 */
class RoomDiffUtil : DiffUtil.ItemCallback<DatabaseRoomEntity>() {
    override fun areItemsTheSame(oldItem: DatabaseRoomEntity, newItem: DatabaseRoomEntity): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: DatabaseRoomEntity, newItem: DatabaseRoomEntity): Boolean = oldItem == newItem
}

class RoomAdapter(context: Context, dc: RoomDiffUtil) : BasicPagedListAdapter<DatabaseRoomEntity, DatabaseRoomViewHolder, RoomDiffUtil, ItemDatabaseRoomBinding>(context, dc) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatabaseRoomViewHolder = DatabaseRoomViewHolder(context, ItemDatabaseRoomBinding.inflate(LayoutInflater.from(context), parent, false))
}