package com.example.myjetpackapplication.business.gesture

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.gesture.R
import com.example.myjetpackapplication.business.gesture.databinding.ActivitySingleGestureBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SingleGestureViewModel(host: SingleGestureActivity, binding: ActivitySingleGestureBinding) : BasicHostViewModel<SingleGestureViewModel, SingleGestureActivity, ActivitySingleGestureBinding>(host, binding) {
    val title = ObservableInt(R.string.single_gesture_title)
}