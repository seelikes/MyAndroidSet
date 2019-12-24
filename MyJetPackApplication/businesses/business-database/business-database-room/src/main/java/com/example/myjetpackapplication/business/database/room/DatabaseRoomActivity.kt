package com.example.myjetpackapplication.business.database.room

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.github.seelikes.android.mvvm.basic.BasicActivity
import com.example.myjetpackapplication.business.database.room.data.DatabaseRoomEntity
import com.example.myjetpackapplication.business.database.room.databinding.ActivityDatabaseRoomBinding
import com.example.myjetpackapplication.business.database.room.event.DeleteEvent
import org.greenrobot.eventbus.Subscribe

/**
 * Created by liutiantian on 2019-05-08 00:06 星期三
 */
@Business(title = "database_room_title", parent = "database_title")
@Route(path = "/business/room")
class DatabaseRoomActivity : BasicActivity<DatabaseRoomActivity, DatabaseRoomViewModel, ActivityDatabaseRoomBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewModelProviders.of(this).get(DatabaseRoomDataModel::class.java).list.observe(this, Observer<PagedList<DatabaseRoomEntity>> {
            if (binding.rvList.adapter is RoomAdapter) {
                (binding.rvList.adapter as RoomAdapter).submitList(it)
            }
        })
    }

    override fun initModel(savedInstanceState: Bundle?): DatabaseRoomViewModel = DatabaseRoomViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_database_room))

    override fun initView(binding: ActivityDatabaseRoomBinding) {
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

    @Subscribe
    fun onEventDelete(event: DeleteEvent) {
        ArchTaskExecutor.getInstance().executeOnDiskIO {
            event.room?.let {
                ViewModelProviders.of(this).get(DatabaseRoomDataModel::class.java).database.roomDao().delete(it)
            }
        }
    }

    fun collapseFloatMenu() {
        binding.floatMenu.collapse()
    }
}
