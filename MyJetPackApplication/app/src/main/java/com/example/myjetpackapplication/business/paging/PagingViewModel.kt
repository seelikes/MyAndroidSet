package com.example.myjetpackapplication.business.paging

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.R
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel
import com.example.myjetpackapplication.databinding.ActivityPagingBinding

class PagingViewModel(host: PagingActivity, binding: ActivityPagingBinding) : BasicHostViewModel<PagingViewModel, PagingActivity, ActivityPagingBinding>(host, binding) {
    val title = ObservableInt(R.string.paging_title)
}