package com.example.myjetpackapplication.business.database.realm

import android.graphics.Rect
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.database.realm.R
import com.example.myjetpackapplication.business.database.realm.databinding.ActivitySingleDatabaseRealmBinding
import com.github.seelikes.android.cache.Cache
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Route(path = "/business/single/database/realm")
class SingleDatabaseRealmActivity : BasicActivity<SingleDatabaseRealmActivity, SingleDatabaseRealmViewModel, ActivitySingleDatabaseRealmBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Cache.strong("123", "123")
    }

    override fun initModel(savedInstanceState: Bundle?): SingleDatabaseRealmViewModel =
        SingleDatabaseRealmViewModel(
            this,
            DataBindingUtil.setContentView(this, R.layout.activity_single_database_realm)
        )

    override fun initView(binding: ActivitySingleDatabaseRealmBinding) {
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
        if (adapter !is SingleDatabaseRealmItemAdapter) {
            adapter =
                SingleDatabaseRealmItemAdapter(this, BusinessManager.getChildren(null)) { item, _ ->
                    item?.let {
                        val children = BusinessManager.getChildren(
                            ViewModelProviders.of(this).get(SingleDatabaseRealmDataModel::class.java).items.value?.get(
                                0
                            )
                        )
                        if (children.isNotEmpty()) {
                            ViewModelProviders.of(this)
                                .get(SingleDatabaseRealmDataModel::class.java).items.value =
                                children
                        } else {
                            item.path?.let { path ->
                                ARouter.getInstance().build(path).navigation()
                            }
                        }
                    }
                }
            binding.rvList.adapter = adapter
        }
    }

    override fun onBackPressed() {
        val pageUp = BusinessManager.tryBack(
            ViewModelProviders.of(this).get(SingleDatabaseRealmDataModel::class.java).items.value?.get(
                0
            ), null
        )
        if (pageUp?.isNotEmpty() == true) {
            ViewModelProviders.of(this).get(SingleDatabaseRealmDataModel::class.java).items.value =
                pageUp
            return
        }
        super.onBackPressed()
    }
}