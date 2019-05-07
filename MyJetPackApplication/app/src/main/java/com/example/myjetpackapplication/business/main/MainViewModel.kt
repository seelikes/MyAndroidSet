package com.example.myjetpackapplication.business.main

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myjetpackapplication.R
import com.example.myjetpackapplication.basic.BasicHostViewModel
import com.example.myjetpackapplication.databinding.ActivityMainBinding
import com.example.myjetpackapplication.utils.mergeArray

@MainItemBean(
    title = R.string.app_name,
    path = "/business/main",
    enable = true,
    children = [
        MainItemBean(
            title = R.string.app_name,
            path = "/business/main",
            enable = true,
            children = [
                MainItemBean(
                    title = R.string.app_name,
                    path = "/business/main",
                    enable = true
                )
            ]
        ),
        MainItemBean(
            title = R.string.app_name,
            path = "/business/main",
            enable = true
        )
    ]
)
class MainViewModel(host: MainActivity, binding: ActivityMainBinding) : BasicHostViewModel<MainViewModel, MainActivity, ActivityMainBinding>(host, binding) {
    val items = ObservableArrayList<MainItemBean>()

    init {
        ViewModelProviders.of(host).get(MainDataModel::class.java).items.observe(host, Observer<Array<MainItemBean>> {
            mergeArray(items, it)
        })
    }

    override fun afterSetToBinding() {
        super.afterSetToBinding()
        ViewModelProviders.of(host).get(MainDataModel::class.java).items.value = javaClass.getAnnotation(MainItemBean::class.java)?.children
    }
}