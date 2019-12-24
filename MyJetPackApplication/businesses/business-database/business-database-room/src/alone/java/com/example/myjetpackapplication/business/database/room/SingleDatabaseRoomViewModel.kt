package com.example.myjetpackapplication.business.database.room

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.database.room.R
import com.example.myjetpackapplication.business.database.room.databinding.ActivitySingleDatabaseRoomBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SingleDatabaseRoomViewModel(
    host: SingleDatabaseRoomActivity,
    binding: ActivitySingleDatabaseRoomBinding
) : BasicHostViewModel<SingleDatabaseRoomViewModel, SingleDatabaseRoomActivity, ActivitySingleDatabaseRoomBinding>(
    host,
    binding
) {
    val title = ObservableInt(R.string.single_database_room_title)
}