package com.example.myjetpackapplication.business.paging

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.paging.R
import com.example.myjetpackapplication.business.paging.databinding.ActivitySinglePagingBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SinglePagingViewModel(host: SinglePagingActivity, binding: ActivitySinglePagingBinding) :
    BasicHostViewModel<SinglePagingViewModel, SinglePagingActivity, ActivitySinglePagingBinding>(
        host,
        binding
    ) {
    val title = ObservableInt(R.string.single_paging_title)
}