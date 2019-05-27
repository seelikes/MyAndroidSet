package com.example.myjetpackapplication.business.speech.baidu

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.R
import com.example.myjetpackapplication.basic.BasicHostViewModel
import com.example.myjetpackapplication.databinding.ActivitySpeechBaiduBinding

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