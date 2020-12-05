package com.example.myjetpackapplication.business.event

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.event.R
import com.example.myjetpackapplication.business.event.databinding.ActivityTouchCancelBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(path = "/business/event/touch/cancel", title = "touch_cancel_view_title", parent = "event_title")
class TouchCancelActivity : BasicActivity<TouchCancelActivity, TouchCancelViewModel, ActivityTouchCancelBinding>() {
    override fun initModel(savedInstanceState: Bundle?): TouchCancelViewModel = TouchCancelViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_touch_cancel))
}