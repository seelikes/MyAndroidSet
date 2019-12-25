package com.example.myjetpackapplication.business.paging

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.paging.databinding.ActivityPagingBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class PagingViewModel(host: PagingActivity, binding: ActivityPagingBinding) : BasicHostViewModel<PagingViewModel, PagingActivity, ActivityPagingBinding>(host, binding) {
    val title = ObservableInt(R.string.paging_title)
}