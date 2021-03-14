package com.example.myjetpackapplication.business.kotlinBasic

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.kotlinBasic.databinding.ActivityKotlinBasicBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class KotlinBasicViewModel(host: KotlinBasicActivity, binding: ActivityKotlinBasicBinding) : BasicHostViewModel<KotlinBasicViewModel, KotlinBasicActivity, ActivityKotlinBasicBinding>(host, binding) {
    val title = ObservableInt(R.string.kotlinBasic_title)
}