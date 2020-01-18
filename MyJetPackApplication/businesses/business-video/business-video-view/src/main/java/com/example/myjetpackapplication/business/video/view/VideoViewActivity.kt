package com.example.myjetpackapplication.business.video.view

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.video.view.R
import com.example.myjetpackapplication.business.video.view.databinding.ActivityVideoViewBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_video_view.view.*

@Business(title = "video_view_title", parent = "video_title")
@Route(path = "/business221/video/view")
class VideoViewActivity : BasicActivity<VideoViewActivity, VideoViewViewModel, ActivityVideoViewBinding>() {
    override fun initModel(savedInstanceState: Bundle?): VideoViewViewModel = VideoViewViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_video_view))

    override fun initView(binding: ActivityVideoViewBinding) {
        super.initView(binding)
        initVideoView(binding)
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
                binding.videoView.setVideoPath(mediaInfo?.data)
                if (!binding.videoView.isPlaying) {
                    binding.videoView.start()
                }
            }
        }
    }

    private fun initVideoView(binding: ActivityVideoViewBinding) {
        binding.videoView.setMediaController(MediaController(this))
        binding.videoView.setOnCompletionListener {
            it.start()
        }
    }
}