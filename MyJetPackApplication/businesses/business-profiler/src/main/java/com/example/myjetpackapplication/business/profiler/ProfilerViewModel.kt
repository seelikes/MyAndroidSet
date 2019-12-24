package com.example.myjetpackapplication.business.profiler

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.profiler.R
import com.example.myjetpackapplication.business.profiler.databinding.ActivityProfilerBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class ProfilerViewModel(host: ProfilerActivity, binding: ActivityProfilerBinding) : BasicHostViewModel<ProfilerViewModel, ProfilerActivity, ActivityProfilerBinding>(host, binding) {
    val title = ObservableInt(R.string.profiler_title)
}