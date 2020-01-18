package com.example.myjetpackapplication.business.video.view

import androidx.databinding.ObservableInt
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myjetpackapplication.business.video.view.R
import com.example.myjetpackapplication.business.video.view.databinding.ActivityVideoViewBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class VideoViewViewModel(host: VideoViewActivity, binding: ActivityVideoViewBinding) : BasicHostViewModel<VideoViewViewModel, VideoViewActivity, ActivityVideoViewBinding>(host, binding) {
    val title = ObservableInt(R.string.video_view_title)

    init {
        ViewModelProvider(host, ViewModelProvider.AndroidViewModelFactory.getInstance(host.application))[VideoViewDataModel::class.java].videos.observe (host, Observer {
            (binding.rvList.adapter as? VideoViewAdapter)?.submitList(it)
        })
    }
}