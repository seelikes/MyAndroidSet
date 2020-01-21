package com.example.myjetpackapplication.business.event

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.event.R
import com.example.myjetpackapplication.business.event.databinding.ActivityTouchCancelBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class TouchCancelViewModel(host: TouchCancelActivity, binding: ActivityTouchCancelBinding) : BasicHostViewModel<TouchCancelViewModel, TouchCancelActivity, ActivityTouchCancelBinding>(host, binding) {
    val title = ObservableInt(R.string.touch_cancel_view_title)
}