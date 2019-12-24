package com.example.myjetpackapplication.business.profiler

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.profiler.R
import com.example.myjetpackapplication.business.profiler.databinding.ActivitySingleProfilerBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SingleProfilerViewModel(
    host: SingleProfilerActivity,
    binding: ActivitySingleProfilerBinding
) : BasicHostViewModel<SingleProfilerViewModel, SingleProfilerActivity, ActivitySingleProfilerBinding>(
    host,
    binding
) {
    val title = ObservableInt(R.string.single_profiler_title)
}