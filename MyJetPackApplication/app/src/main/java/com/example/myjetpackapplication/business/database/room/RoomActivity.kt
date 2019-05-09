package com.example.myjetpackapplication.business.database.room

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.R
import com.example.myjetpackapplication.basic.BasicActivity
import com.example.myjetpackapplication.business.database.room.data.RoomEntity
import com.example.myjetpackapplication.databinding.ActivityRoomBinding

/**
 * Created by liutiantian on 2019-05-08 00:06 星期三
 */
@Route(path = "/business/room")
class RoomActivity : BasicActivity<RoomActivity, RoomViewModel, ActivityRoomBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewModelProviders.of(this).get(RoomDataModel::class.java).list.observe(this, Observer<PagedList<RoomEntity>> {

        })
    }

    override fun initModel(savedInstanceState: Bundle?): RoomViewModel = RoomViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_room))

    override fun initView(binding: ActivityRoomBinding) {
        super.initView(binding)
        if (binding.rvList.layoutManager == null) {
            binding.rvList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
        if (binding.rvList.itemDecorationCount == 0) {
            binding.rvList.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    if (parent.getChildAdapterPosition(view) != 0) {
                        outRect.top = 2
                    }
                }
            })
        }
        if (binding.rvList.adapter !is RoomAdapter) {
            binding.rvList.adapter = RoomAdapter(this, RoomDiffUtil())
        }
    }
}
