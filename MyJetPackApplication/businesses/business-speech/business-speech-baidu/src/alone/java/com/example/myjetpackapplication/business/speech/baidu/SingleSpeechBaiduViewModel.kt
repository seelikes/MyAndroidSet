package com.example.myjetpackapplication.business.speech.baidu

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.speech.baidu.R
import com.example.myjetpackapplication.business.speech.baidu.databinding.ActivitySingleSpeechBaiduBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SingleSpeechBaiduViewModel(
    host: SingleSpeechBaiduActivity,
    binding: ActivitySingleSpeechBaiduBinding
) : BasicHostViewModel<SingleSpeechBaiduViewModel, SingleSpeechBaiduActivity, ActivitySingleSpeechBaiduBinding>(
    host,
    binding
) {
    val title = ObservableInt(R.string.single_speech_baidu_title)
}