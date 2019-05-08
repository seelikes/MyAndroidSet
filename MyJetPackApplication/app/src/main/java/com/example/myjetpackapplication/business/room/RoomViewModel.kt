package com.example.myjetpackapplication.business.room

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.R
import com.example.myjetpackapplication.basic.BasicHostViewModel
import com.example.myjetpackapplication.databinding.ActivityRoomBinding

/**
 * Created by liutiantian on 2019-05-08 00:11 星期三
 */
class RoomViewModel(host: RoomActivity, binding: ActivityRoomBinding) : BasicHostViewModel<RoomViewModel, RoomActivity, ActivityRoomBinding>(host, binding) {
    val title = ObservableInt(R.string.room_title)
}