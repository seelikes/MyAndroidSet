package com.example.myjetpackapplication.business.speech

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.speech.databinding.ActivitySpeechBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(path = "/business/speech", title = "speech_title")
class SpeechActivity : BasicActivity<SpeechActivity, SpeechViewModel, ActivitySpeechBinding>() {
    override fun initModel(savedInstanceState: Bundle?): SpeechViewModel =
        SpeechViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_speech))
}