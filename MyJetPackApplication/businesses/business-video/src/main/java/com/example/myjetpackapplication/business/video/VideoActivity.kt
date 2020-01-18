package com.example.myjetpackapplication.business.video

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.video.R
import com.example.myjetpackapplication.business.video.databinding.ActivityVideoBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(title = "video_title")
@Route(path = "/business/video")
class VideoActivity : BasicActivity<VideoActivity, VideoViewModel, ActivityVideoBinding>() {
    override fun initModel(savedInstanceState: Bundle?): VideoViewModel = VideoViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_video))
}