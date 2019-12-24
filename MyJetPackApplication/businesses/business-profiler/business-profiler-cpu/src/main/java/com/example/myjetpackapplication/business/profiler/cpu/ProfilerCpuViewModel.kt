package com.example.myjetpackapplication.business.profiler.cpu

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.profiler.cpu.databinding.ActivityProfilerCpuBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel
import kotlin.math.abs
import kotlin.random.Random.Default.nextLong

class ProfilerCpuViewModel(host: ProfilerCPUActivity, binding: ActivityProfilerCpuBinding) : BasicHostViewModel<ProfilerCpuViewModel, ProfilerCPUActivity, ActivityProfilerCpuBinding>(host, binding) {
    val title = ObservableInt(R.string.profiler_cpu_title)

    fun onUiClickLongExecuteMethod() {
        runOverAfter(abs(nextLong()) % 3072)
    }

    fun onUiClickHighFrequencyExecuteMethod() {
        for (i in 0..724) {
            runOverAfter(abs(nextLong()) % 12)
        }
    }

    private fun runOverAfter(time: Long) {
        Thread.sleep(time)
    }
}