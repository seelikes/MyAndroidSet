package com.example.myjetpackapplication.business.view

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.view.R
import com.example.myjetpackapplication.business.view.databinding.ActivitySingleViewBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SingleViewViewModel(host: SingleViewActivity, binding: ActivitySingleViewBinding) :
    BasicHostViewModel<SingleViewViewModel, SingleViewActivity, ActivitySingleViewBinding>(
        host,
        binding
    ) {
    val title = ObservableInt(R.string.single_view_title)
}