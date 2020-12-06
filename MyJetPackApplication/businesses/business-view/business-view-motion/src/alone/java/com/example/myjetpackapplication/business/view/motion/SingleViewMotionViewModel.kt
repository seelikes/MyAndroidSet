package com.example.myjetpackapplication.business.view.motion

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.view.motion.R
import com.example.myjetpackapplication.business.view.motion.databinding.ActivitySingleViewMotionBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SingleViewMotionViewModel(
    host: SingleViewMotionActivity,
    binding: ActivitySingleViewMotionBinding
) : BasicHostViewModel<SingleViewMotionViewModel, SingleViewMotionActivity, ActivitySingleViewMotionBinding>(
    host,
    binding
) {
    val title = ObservableInt(R.string.single_view_motion_title)
}