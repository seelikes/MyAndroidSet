package com.example.myjetpackapplication.business.video

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.video.databinding.ActivityVideoBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(path = "/business/video", title = "video_title")
class VideoActivity : BasicActivity<VideoActivity, VideoViewModel, ActivityVideoBinding>() {
    override fun initModel(savedInstanceState: Bundle?): VideoViewModel = VideoViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_video))
}