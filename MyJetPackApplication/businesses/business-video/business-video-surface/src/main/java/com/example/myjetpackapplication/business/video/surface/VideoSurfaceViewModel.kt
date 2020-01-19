package com.example.myjetpackapplication.business.video.surface

import androidx.databinding.ObservableInt
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myjetpackapplication.business.video.surface.databinding.ActivityVideoSurfaceBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class VideoSurfaceViewModel(host: VideoSurfaceActivity, binding: ActivityVideoSurfaceBinding) : BasicHostViewModel<VideoSurfaceViewModel, VideoSurfaceActivity, ActivityVideoSurfaceBinding>(host, binding) {
    val title = ObservableInt(R.string.video_surface_title)

    init {
        ViewModelProvider(host, ViewModelProvider.AndroidViewModelFactory.getInstance(host.application))[VideoSurfaceDataModel::class.java].videos.observe (host, Observer {
            (binding.rvList.adapter as? VideoSurfaceAdapter)?.submitList(it)
        })
    }
}