package com.example.myjetpackapplication.business.speech

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.speech.databinding.ActivitySpeechBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SpeechViewModel(host: SpeechActivity, binding: ActivitySpeechBinding) :
    BasicHostViewModel<SpeechViewModel, SpeechActivity, ActivitySpeechBinding>(host, binding) {
    val title = ObservableInt(R.string.speech_title)
}