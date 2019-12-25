package com.example.myjetpackapplication.business.speech

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.speech.R
import com.example.myjetpackapplication.business.speech.databinding.ActivitySingleSpeechBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SingleSpeechViewModel(host: SingleSpeechActivity, binding: ActivitySingleSpeechBinding) :
    BasicHostViewModel<SingleSpeechViewModel, SingleSpeechActivity, ActivitySingleSpeechBinding>(
        host,
        binding
    ) {
    val title = ObservableInt(R.string.single_speech_title)
}