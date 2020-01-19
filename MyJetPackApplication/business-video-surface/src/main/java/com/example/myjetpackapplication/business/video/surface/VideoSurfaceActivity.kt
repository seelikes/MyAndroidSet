package com.example.myjetpackapplication.business.video.surface

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.video.surface.R
import com.example.myjetpackapplication.business.video.surface.databinding.ActivityVideoSurfaceBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(title = "video_surface_title")
@Route(path = "/business/video_surface")
class VideoSurfaceActivity :
    BasicActivity<VideoSurfaceActivity, VideoSurfaceViewModel, ActivityVideoSurfaceBinding>() {
    override fun initModel(savedInstanceState: Bundle?): VideoSurfaceViewModel =
        VideoSurfaceViewModel(
            this,
            DataBindingUtil.setContentView(this, R.layout.activity_video_surface)
        )
}