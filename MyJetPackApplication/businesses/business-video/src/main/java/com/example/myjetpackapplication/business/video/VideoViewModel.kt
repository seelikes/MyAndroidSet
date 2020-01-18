package com.example.myjetpackapplication.business.video

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.video.databinding.ActivityVideoBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class VideoViewModel(host: VideoActivity, binding: ActivityVideoBinding) : BasicHostViewModel<VideoViewModel, VideoActivity, ActivityVideoBinding>(host, binding) {
    val title = ObservableInt(R.string.video_title)
}