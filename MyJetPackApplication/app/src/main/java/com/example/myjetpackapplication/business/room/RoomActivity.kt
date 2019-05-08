package com.example.myjetpackapplication.business.room

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.R
import com.example.myjetpackapplication.basic.BasicActivity
import com.example.myjetpackapplication.databinding.ActivityRoomBinding
import javax.inject.Inject

/**
 * Created by liutiantian on 2019-05-08 00:06 星期三
 */
@Route(path = "/business/room")
class RoomActivity : BasicActivity<RoomActivity, RoomViewModel, ActivityRoomBinding>() {
    @Inject
    lateinit var database: RoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerRoomComponent.builder().roomModule(RoomModule(this)).build().inject(this)
    }

    override fun initModel(savedInstanceState: Bundle?): RoomViewModel = RoomViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_room))
}