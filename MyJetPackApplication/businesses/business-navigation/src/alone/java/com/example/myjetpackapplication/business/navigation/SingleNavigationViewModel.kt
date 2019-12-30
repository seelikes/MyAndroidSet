package com.example.myjetpackapplication.business.navigation

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.navigation.databinding.ActivitySingleNavigationBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SingleNavigationViewModel(
    host: SingleNavigationActivity,
    binding: ActivitySingleNavigationBinding
) : BasicHostViewModel<SingleNavigationViewModel, SingleNavigationActivity, ActivitySingleNavigationBinding>(
    host,
    binding
) {
    val title = ObservableInt(R.string.single_navigation_title)
}