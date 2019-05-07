package com.example.myjetpackapplication.business.main

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.myjetpackapplication.R
import com.example.myjetpackapplication.basic.BasicActivity
import com.example.myjetpackapplication.databinding.ActivityMainBinding
import com.example.myjetpackapplication.utils.connect

@Route(path = "/business/main")
class  MainActivity : BasicActivity<MainActivity, MainViewModel, ActivityMainBinding>() {
    override fun initModel(savedInstanceState: Bundle?): MainViewModel = MainViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_main))

    override fun initView(binding: ActivityMainBinding) {
        if (binding.rvList.layoutManager == null) {
            binding.rvList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
        if (binding.rvList.itemDecorationCount == 0) {
            binding.rvList.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    if (parent.getChildAdapterPosition(view) != 0) {
                        outRect.top = 2
                    }
                }
            })
        }
        var adapter = binding.rvList.adapter
        if (adapter !is MainItemAdapter) {
            adapter = MainItemAdapter(this, model.items) { item, position ->
                if (item.children.isNotEmpty()) {
                    ViewModelProviders.of(this).get(MainDataModel::class.java).items.value = item.children
                }
                else {
                    ARouter.getInstance().build(item.path).navigation()
                }
            }
            connect(adapter, model.items)
            binding.rvList.adapter = adapter
        }
    }
}
