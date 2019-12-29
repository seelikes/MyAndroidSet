package com.example.myjetpackapplication.business.paging

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.paging.R
import com.example.myjetpackapplication.business.paging.databinding.ActivitySinglePagingBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Route(path = "/business/single/paging")
class SinglePagingActivity :
    BasicActivity<SinglePagingActivity, SinglePagingViewModel, ActivitySinglePagingBinding>() {
    override fun initModel(savedInstanceState: Bundle?): SinglePagingViewModel =
        SinglePagingViewModel(
            this,
            DataBindingUtil.setContentView(this, R.layout.activity_single_paging)
        )

    override fun initView(binding: ActivitySinglePagingBinding) {
        super.initView(binding)
        if (binding.rvList.layoutManager == null) {
            binding.rvList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
        if (binding.rvList.itemDecorationCount == 0) {
            binding.rvList.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    if (parent.getChildAdapterPosition(view) != 0) {
                        outRect.top = 2
                    }
                }
            })
        }
        var adapter = binding.rvList.adapter
        if (adapter !is SinglePagingItemAdapter) {
            adapter = SinglePagingItemAdapter(this, BusinessManager.getChildren(null)) { item, _ ->
                item?.let {
                    val children = BusinessManager.getChildren(
                        ViewModelProviders.of(this).get(SinglePagingDataModel::class.java).items.value?.get(
                            0
                        )
                    )
                    if (children.isNotEmpty()) {
                        ViewModelProviders.of(this).get(SinglePagingDataModel::class.java)
                            .items.value = children
                    } else {
                        ARouter.getInstance().build(item.path).navigation()
                    }
                }
            }
            binding.rvList.adapter = adapter
        }
    }

    override fun onBackPressed() {
        val pageUp = BusinessManager.tryBack(
            ViewModelProviders.of(this).get(SinglePagingDataModel::class.java).items.value?.get(0),
            null
        )
        if (pageUp?.isNotEmpty() == true) {
            ViewModelProviders.of(this).get(SinglePagingDataModel::class.java).items.value = pageUp
            return
        }
        super.onBackPressed()
    }
}