package com.example.myjetpackapplication.business.anr

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.anr.R
import com.example.myjetpackapplication.business.anr.databinding.ActivityAnrBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class AnrViewModel(host: AnrActivity, binding: ActivityAnrBinding) :
    BasicHostViewModel<AnrViewModel, AnrActivity, ActivityAnrBinding>(host, binding) {
    val title = ObservableInt(R.string.anr_title)
}