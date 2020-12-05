package com.example.myjetpackapplication.business.paging

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.github.seelikes.android.mvvm.basic.BasicActivity
import com.example.myjetpackapplication.business.paging.data.PagingEntity
import com.example.myjetpackapplication.business.paging.databinding.ActivityPagingBinding

@Business(path = "/business/paging", title = "paging_title")
class PagingActivity : BasicActivity<PagingActivity, PagingViewModel, ActivityPagingBinding>() {
    override fun initModel(savedInstanceState: Bundle?): PagingViewModel = PagingViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_paging))

    override fun initView(binding: ActivityPagingBinding) {
        super.initView(binding)
        if (binding.rvList.layoutManager == null) {
            binding.rvList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
        if (binding.rvList.itemDecorationCount == 0) {
            binding.rvList.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    if (parent.getChildAdapterPosition(view) != 0) {
                        outRect.top = 4
                    }
                }
            })
        }
        if (binding.rvList.adapter !is PagingAdapter) {
            binding.rvList.adapter = PagingAdapter(this)
            ViewModelProviders.of(this).get(PagingDataModel::class.java).list.observe(this, Observer<PagedList<PagingEntity>> {
                (binding.rvList.adapter as PagingAdapter).submitList(it)
            })
        }
    }
}