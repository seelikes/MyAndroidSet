package com.example.myjetpackapplication.business.video.surface

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.video.surface.R
import com.example.myjetpackapplication.business.video.surface.databinding.ActivityVideoSurfaceBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class VideoSurfaceViewModel(host: VideoSurfaceActivity, binding: ActivityVideoSurfaceBinding) :
    BasicHostViewModel<VideoSurfaceViewModel, VideoSurfaceActivity, ActivityVideoSurfaceBinding>(
        host,
        binding
    ) {
    val title = ObservableInt(R.string.video_surface_title)
}