package com.example.myjetpackapplication.business.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.view.databinding.ActivityViewBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(path = "/business/view", title = "view_title")
class ViewActivity : BasicActivity<ViewActivity, ViewViewModel, ActivityViewBinding>() {
    override fun initModel(savedInstanceState: Bundle?): ViewViewModel =
        ViewViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_view))
}