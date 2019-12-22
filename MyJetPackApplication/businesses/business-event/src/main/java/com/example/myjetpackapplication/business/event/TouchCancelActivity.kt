package com.example.myjetpackapplication.business.event

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.business.event.R
import com.example.myjetpackapplication.business.event.databinding.ActivityTouchCancelBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Route(path = "/business14/event/touch/cancel")
class TouchCancelActivity : BasicActivity<TouchCancelActivity, TouchCancelViewModel, ActivityTouchCancelBinding>() {
    override fun initModel(savedInstanceState: Bundle?): TouchCancelViewModel = TouchCancelViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_touch_cancel))
}