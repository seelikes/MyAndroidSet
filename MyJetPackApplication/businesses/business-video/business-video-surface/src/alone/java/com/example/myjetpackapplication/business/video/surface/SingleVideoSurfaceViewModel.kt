package com.example.myjetpackapplication.business.video.surface

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.video.surface.databinding.ActivitySingleVideoSurfaceBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SingleVideoSurfaceViewModel(
    host: SingleVideoSurfaceActivity,
    binding: ActivitySingleVideoSurfaceBinding
) : BasicHostViewModel<SingleVideoSurfaceViewModel, SingleVideoSurfaceActivity, ActivitySingleVideoSurfaceBinding>(
    host,
    binding
) {
    val title = ObservableInt(R.string.single_video_surface_title)
}