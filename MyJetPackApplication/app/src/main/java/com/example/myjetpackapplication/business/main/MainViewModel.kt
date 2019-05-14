package com.example.myjetpackapplication.business.main

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myjetpackapplication.R
import com.example.myjetpackapplication.basic.BasicHostViewModel
import com.example.myjetpackapplication.business.main.data.MainItemBean
import com.example.myjetpackapplication.databinding.ActivityMainBinding
import com.example.myjetpackapplication.utils.mergeArray

private val rootItem = MainItemBean(
    title = R.string.app_name,
    path = "/business/main",
    children = arrayOf(
        MainItemBean(
            title = R.string.database_title,
            children = arrayOf(
                MainItemBean(
                    title = R.string.room_title,
                    path = "/business/room"
                )
            )
        ),
        MainItemBean(
            title = R.string.paging_title,
            path = "/business/paging"
        )
    )
)

fun pageUp(item: MainItemBean, currentPage: Array<MainItemBean> = rootItem.children): Array<MainItemBean> {
    if (currentPage.isNotEmpty()) {
        for (child in currentPage) {
            if (child.children.isNotEmpty()) {
                for (grandson in child.children) {
                    if (grandson == item) {
                        return currentPage
                    }
                }
                val pageUp = pageUp(item, child.children)
                if (pageUp.isNotEmpty()) {
                    return pageUp
                }
            }
        }
    }
    return arrayOf()
}

class MainViewModel(host: MainActivity, binding: ActivityMainBinding) : BasicHostViewModel<MainViewModel, MainActivity, ActivityMainBinding>(host, binding) {
    val items = ObservableArrayList<MainItemBean>()

    init {
        ViewModelProviders.of(host).get(MainDataModel::class.java).items.observe(host, Observer<Array<MainItemBean>> {
            mergeArray(items, it)
        })
    }

    override fun afterSetToBinding() {
        super.afterSetToBinding()
        ViewModelProviders.of(host).get(MainDataModel::class.java).items.value = rootItem.children
    }
}