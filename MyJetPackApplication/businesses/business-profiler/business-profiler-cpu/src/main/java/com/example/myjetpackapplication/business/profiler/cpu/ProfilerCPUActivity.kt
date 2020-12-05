package com.example.myjetpackapplication.business.profiler.cpu

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.profiler.cpu.databinding.ActivityProfilerCpuBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(path = "/business/profiler/cpu", title = "profiler_cpu_title", parent = "profiler_title")
class ProfilerCPUActivity : BasicActivity<ProfilerCPUActivity, ProfilerCpuViewModel, ActivityProfilerCpuBinding>() {
    override fun initModel(savedInstanceState: Bundle?): ProfilerCpuViewModel = ProfilerCpuViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_profiler_cpu))
}