package com.example.myjetpackapplication.business.database.room

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.myjetpackapplication.basic.BasicPagedListAdapter
import com.example.myjetpackapplication.business.database.room.data.RoomEntity
import com.example.myjetpackapplication.databinding.ItemRoomBinding

/**
 * Created by liutiantian on 2019-05-09 13:36 星期四
 */
class RoomDiffUtil : DiffUtil.ItemCallback<RoomEntity>() {
    override fun areItemsTheSame(oldItem: RoomEntity, newItem: RoomEntity): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RoomEntity, newItem: RoomEntity): Boolean = oldItem == newItem
}

class RoomAdapter(context: Context, dc: RoomDiffUtil) : BasicPagedListAdapter<RoomEntity, RoomViewHolder, RoomDiffUtil, ItemRoomBinding>(context, dc) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder = RoomViewHolder(context, ItemRoomBinding.inflate(LayoutInflater.from(context), parent, false))
}