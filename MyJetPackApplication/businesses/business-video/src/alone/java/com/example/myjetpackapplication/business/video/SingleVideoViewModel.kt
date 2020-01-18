package com.example.myjetpackapplication.business.video

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.video.R
import com.example.myjetpackapplication.business.video.databinding.ActivitySingleVideoBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SingleVideoViewModel(host: SingleVideoActivity, binding: ActivitySingleVideoBinding) :
    BasicHostViewModel<SingleVideoViewModel, SingleVideoActivity, ActivitySingleVideoBinding>(
        host,
        binding
    ) {
    val title = ObservableInt(R.string.single_video_title)
}