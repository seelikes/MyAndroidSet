package com.example.myjetpackapplication.business.gesture

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
import com.example.myjetpackapplication.business.gesture.R
import com.example.myjetpackapplication.business.gesture.databinding.ActivitySingleGestureBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Route(path = "/business/single/gesture")
class SingleGestureActivity : BasicActivity<SingleGestureActivity, SingleGestureViewModel, ActivitySingleGestureBinding>() {
    override fun initModel(savedInstanceState: Bundle?): SingleGestureViewModel =
        SingleGestureViewModel(
            this,
            DataBindingUtil.setContentView(this, R.layout.activity_single_gesture)
        )

    override fun initView(binding: ActivitySingleGestureBinding) {
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
        if (adapter !is SingleGestureItemAdapter) {
            adapter = SingleGestureItemAdapter(this, BusinessManager.getChildren(null)) { item, _ ->
                item?.let {
                    val children = BusinessManager.getChildren(it)
                    if (children.isNotEmpty()) {
                        ViewModelProviders.of(this).get(SingleGestureDataModel::class.java)
                            .items.value = children
                    } else {
                        ARouter.getInstance().build(it.path).navigation()
                    }
                }
            }
            binding.rvList.adapter = adapter
        }
    }
}