package com.example.myjetpackapplication.business.view.motion

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.view.motion.R
import com.example.myjetpackapplication.business.view.motion.databinding.ActivityViewMotionBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class ViewMotionViewModel(host: ViewMotionActivity, binding: ActivityViewMotionBinding) :
    BasicHostViewModel<ViewMotionViewModel, ViewMotionActivity, ActivityViewMotionBinding>(
        host,
        binding
    ) {
    val title = ObservableInt(R.string.view_motion_title)
}