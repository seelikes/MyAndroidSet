package com.example.myjetpackapplication.business.video.view

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.video.view.databinding.ActivityVideoViewBinding
import com.example.myjetpackapplication.business.video.view.event.VideoListScrollEvent
import com.github.seelikes.android.mvvm.basic.BasicActivity
import com.orhanobut.logger.Logger
import org.greenrobot.eventbus.EventBus

@Business(title = "video_view_title", parent = "video_title")
@Route(path = "/business221/video/view")
class VideoViewActivity : BasicActivity<VideoViewActivity, VideoViewViewModel, ActivityVideoViewBinding>() {
    override fun initModel(savedInstanceState: Bundle?): VideoViewViewModel = VideoViewViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_video_view))

    override fun initView(binding: ActivityVideoViewBinding) {
        super.initView(binding)
        initList(binding)
    }

    private fun initList(binding: ActivityVideoViewBinding) {
        if (binding.rvList.layoutManager == null) {
            binding.rvList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
        if (binding.rvList.itemDecorationCount == 0) {
            binding.rvList.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    if (parent.getChildAdapterPosition(view) != 0) {
                        outRect.top = 4
                    }
                }
            })
        }
        if (binding.rvList.adapter !is VideoViewAdapter) {
            binding.rvList.adapter = VideoViewAdapter(this) { mediaInfo, position ->
                Logger.i("X11211X, position: $position; mediaInfo: $mediaInfo")
                EventBus.getDefault().post(mediaInfo)
            }
        }
        binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                EventBus.getDefault().post(VideoListScrollEvent(newState))
            }
        })
    }
}