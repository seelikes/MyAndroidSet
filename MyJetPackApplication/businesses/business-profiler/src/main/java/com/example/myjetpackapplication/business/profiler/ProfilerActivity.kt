package com.example.myjetpackapplication.business.profiler

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.profiler.R
import com.example.myjetpackapplication.business.profiler.databinding.ActivityProfilerBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(title = "profiler_title")
class ProfilerActivity : BasicActivity<ProfilerActivity, ProfilerViewModel, ActivityProfilerBinding>() {
    override fun initModel(savedInstanceState: Bundle?): ProfilerViewModel = ProfilerViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_profiler))
}