package com.example.myjetpackapplication.business.view.motion

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.view.motion.R
import com.example.myjetpackapplication.business.view.motion.databinding.ActivityViewMotionBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(path = "/business/view/motion", title = "view_motion_title", parent = "view_title")
class ViewMotionActivity :
    BasicActivity<ViewMotionActivity, ViewMotionViewModel, ActivityViewMotionBinding>() {
    override fun initModel(savedInstanceState: Bundle?): ViewMotionViewModel = ViewMotionViewModel(
        this,
        DataBindingUtil.setContentView(this, R.layout.activity_view_motion)
    )
}