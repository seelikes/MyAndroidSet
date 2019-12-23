package com.example.myjetpackapplication.business.main

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem
import com.example.myjetpackapplication.annotationprocessor.business.api.BusinessApi
import com.example.myjetpackapplication.databinding.ActivityMainBinding
import com.example.myjetpackapplication.utils.mergeArray
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class MainViewModel(host: MainActivity, binding: ActivityMainBinding) : BasicHostViewModel<MainViewModel, MainActivity, ActivityMainBinding>(host, binding) {
    val items = ObservableArrayList<BusinessItem>()

    init {
        ViewModelProviders.of(host).get(MainDataModel::class.java).items.observe(host, Observer<List<BusinessItem>> {
            mergeArray(items, it ?: listOf())
        })
    }

    override fun afterSetToBinding() {
        super.afterSetToBinding()
        ViewModelProviders.of(host).get(MainDataModel::class.java).items.value = BusinessApi.getChildren(null)
    }
}