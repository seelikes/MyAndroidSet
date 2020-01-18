package com.example.myjetpackapplication.business.video.view

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.video.view.R
import com.example.myjetpackapplication.business.video.view.databinding.ActivitySingleVideoViewBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SingleVideoViewViewModel(
    host: SingleVideoViewActivity,
    binding: ActivitySingleVideoViewBinding
) : BasicHostViewModel<SingleVideoViewViewModel, SingleVideoViewActivity, ActivitySingleVideoViewBinding>(
    host,
    binding
) {
    val title = ObservableInt(R.string.single_video_view_title)
}