package com.example.myjetpackapplication.business.navigation

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.navigation.databinding.ActivityNavigationBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class NavigationViewModel(host: NavigationActivity, binding: ActivityNavigationBinding) : BasicHostViewModel<NavigationViewModel, NavigationActivity, ActivityNavigationBinding>(host, binding) {
    val title = ObservableInt(R.string.navigation_title)
}