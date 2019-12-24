package com.example.myjetpackapplication.business.profiler.cpu

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.profiler.cpu.R
import com.example.myjetpackapplication.business.profiler.cpu.databinding.ActivitySingleProfilerCpuBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SingleProfilerCpuViewModel(
    host: SingleProfilerCpuActivity,
    binding: ActivitySingleProfilerCpuBinding
) : BasicHostViewModel<SingleProfilerCpuViewModel, SingleProfilerCpuActivity, ActivitySingleProfilerCpuBinding>(
    host,
    binding
) {
    val title = ObservableInt(R.string.single_profiler_cpu_title)
}