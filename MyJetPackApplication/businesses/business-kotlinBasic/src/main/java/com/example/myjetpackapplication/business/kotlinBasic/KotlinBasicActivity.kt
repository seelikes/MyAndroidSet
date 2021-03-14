package com.example.myjetpackapplication.business.kotlinBasic

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.kotlinBasic.databinding.ActivityKotlinBasicBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(path = "/business/kotlinBasic", title = "kotlinBasic_title")
class KotlinBasicActivity : BasicActivity<KotlinBasicActivity, KotlinBasicViewModel, ActivityKotlinBasicBinding>() {
    override fun initModel(savedInstanceState: Bundle?): KotlinBasicViewModel {
        return KotlinBasicViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_kotlin_basic))
    }
}