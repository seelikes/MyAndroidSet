package com.example.myjetpackapplication.business.profiler.cpu

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.R
import com.example.myjetpackapplication.databinding.ActivityCpuBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel
import kotlin.math.abs
import kotlin.random.Random.Default.nextLong

class CpuViewModel(host: CPUActivity, binding: ActivityCpuBinding) : BasicHostViewModel<CpuViewModel, CPUActivity, ActivityCpuBinding>(host, binding) {
    val title = ObservableInt(R.string.cpu_title)

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