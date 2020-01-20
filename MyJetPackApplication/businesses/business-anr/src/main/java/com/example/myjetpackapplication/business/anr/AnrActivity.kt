package com.example.myjetpackapplication.business.anr

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.anr.databinding.ActivityAnrBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(title = "anr_title")
@Route(path = "/business13/anr")
class AnrActivity : BasicActivity<AnrActivity, AnrViewModel, ActivityAnrBinding>() {
    override fun initModel(savedInstanceState: Bundle?): AnrViewModel = AnrViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_anr))

    fun onTriggerClick(view : View) {
        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < 10000) {
            Thread.sleep(124)
        }
    }
}