package com.example.myjetpackapplication.business.view.motion

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.annotationprocessor.business.api.BusinessApi
import com.example.myjetpackapplication.business.view.motion.databinding.ActivitySingleViewMotionBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(path = "/business/single/view/motion")
class SingleViewMotionActivity :
    BasicActivity<SingleViewMotionActivity, SingleViewMotionViewModel, ActivitySingleViewMotionBinding>() {
    override fun initModel(savedInstanceState: Bundle?): SingleViewMotionViewModel =
        SingleViewMotionViewModel(
            this,
            DataBindingUtil.setContentView(this, R.layout.activity_single_view_motion)
        )

    override fun initView(binding: ActivitySingleViewMotionBinding) {
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
        if (adapter !is SingleViewMotionItemAdapter) {
            adapter = SingleViewMotionItemAdapter(this, BusinessApi.getChildren(null)) { item, _ ->
                item?.let {
                    val children = BusinessApi.getChildren(
                        ViewModelProviders.of(this).get(SingleViewMotionDataModel::class.java).items.value?.get(
                            0
                        )
                    )
                    if (!children.isNullOrEmpty()) {
                        ViewModelProviders.of(this).get(SingleViewMotionDataModel::class.java)
                            .items.value = children
                    } else {
                        item.path?.let { path ->
                            BusinessApi.go(this, path)
                        }
                    }
                }
            }
            binding.rvList.adapter = adapter
        }
    }

    override fun onBackPressed() {
        val pageUp = BusinessApi.tryBack(
            ViewModelProviders.of(this).get(SingleViewMotionDataModel::class.java).items.value?.get(
                0
            ), null
        )
        if (pageUp?.isNotEmpty() == true) {
            ViewModelProviders.of(this).get(SingleViewMotionDataModel::class.java).items.value =
                pageUp
            return
        }
        super.onBackPressed()
    }
}