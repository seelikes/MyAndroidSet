package com.example.myjetpackapplication.business.navigation

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.navigation.databinding.ActivityNavigationBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(path = "/business/navigation", title = "navigation_title")
class NavigationActivity : BasicActivity<NavigationActivity, NavigationViewModel, ActivityNavigationBinding>() {
    override fun initModel(savedInstanceState: Bundle?): NavigationViewModel = NavigationViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_navigation))

    override fun onSupportNavigateUp(): Boolean = findNavController(R.navigation.navigation_graph).navigateUp()
}