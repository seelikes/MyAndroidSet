package com.example.myjetpackapplication.business.view

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.view.R
import com.example.myjetpackapplication.business.view.databinding.ActivityViewBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class ViewViewModel(host: ViewActivity, binding: ActivityViewBinding) :
    BasicHostViewModel<ViewViewModel, ViewActivity, ActivityViewBinding>(host, binding) {
    val title = ObservableInt(R.string.view_title)
}