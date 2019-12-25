package com.example.myjetpackapplication.business.speech.baidu

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.speech.baidu.databinding.ActivitySpeechBaiduBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SpeechBaiduViewModel(host: SpeechBaiduActivity, binding: ActivitySpeechBaiduBinding) : BasicHostViewModel<SpeechBaiduViewModel, SpeechBaiduActivity, ActivitySpeechBaiduBinding>(host, binding) {
    val title = ObservableInt(R.string.speech_baidu_title)
    val speak = ObservableField<String>()

    fun onUiClickSpeech() {
        val speech = this.speak.get()
        if (speech != null && speech.isNotEmpty()) {
            host.synthesizer.speak(speech)
        }
    }
}