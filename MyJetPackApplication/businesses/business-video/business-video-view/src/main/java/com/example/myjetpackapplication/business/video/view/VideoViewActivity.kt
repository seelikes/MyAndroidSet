package com.example.myjetpackapplication.business.video.view

import android.Manifest
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.video.view.databinding.ActivityVideoViewBinding
import com.example.myjetpackapplication.business.video.view.event.VideoListScrollEvent
import com.github.seelikes.android.mvvm.basic.BasicActivity
import com.orhanobut.logger.Logger
import com.yanzhenjie.permission.AndPermission
import org.greenrobot.eventbus.EventBus

@Business(title = "video_view_title", path = "/business/video/view", parent = "video_title")
class VideoViewActivity : BasicActivity<VideoViewActivity, VideoViewViewModel, ActivityVideoViewBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .onGranted {
                    Toast.makeText(this, "external storage read permission granted", Toast.LENGTH_LONG).show()
                }
                .onDenied {
                    Toast.makeText(this, "external storage read permission denied, activity finish", Toast.LENGTH_LONG).show()
                    finish()
                }
                .start()
        }
    }

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
//                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
//                    Debug.startMethodTracing(FileUtils.getInstance().locateFile(externalCacheDir, "tracing", "${System.currentTimeMillis()}").absolutePath)
//                }
                EventBus.getDefault().post(VideoListScrollEvent(newState))
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    Debug.stopMethodTracing()
//                }
            }
        })
    }
}