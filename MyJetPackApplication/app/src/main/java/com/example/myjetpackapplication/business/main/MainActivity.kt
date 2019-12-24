package com.example.myjetpackapplication.business.main

import android.Manifest
import android.graphics.Rect
import android.os.Bundle
import android.os.Debug
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.myjetpackapplication.BuildConfig
import com.example.myjetpackapplication.R
import com.example.myjetpackapplication.annotationprocessor.business.api.BusinessApi
import com.example.myjetpackapplication.databinding.ActivityMainBinding
import com.example.myjetpackapplication.sophix.work.SophixWorker
import com.example.myjetpackapplication.utils.connect
import com.github.seelikes.android.mvvm.basic.BasicActivity
import com.orhanobut.logger.Logger
import com.yanzhenjie.permission.AndPermission
import java.util.concurrent.TimeUnit

@Route(path = "/business/main")
class  MainActivity : BasicActivity<MainActivity, MainViewModel, ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) {
            Debug.startMethodTracing()
        }
        AndPermission.with(this)
            .runtime()
            .permission(Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .onGranted {
                Logger.i("onCreate.onGranted.UL4886122304953LP, enter")
                with(WorkManager.getInstance(this)) {
                    val constants = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).setRequiresBatteryNotLow(true).build()
                    enqueue(PeriodicWorkRequestBuilder<SophixWorker>(PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS).setConstraints(constants).build())
                }
            }
            .start()
    }

    override fun onDestroy() {
        WorkManager.getInstance(this).cancelAllWork()
        super.onDestroy()
        if (BuildConfig.DEBUG) {
            Debug.stopMethodTracing()
        }
    }

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
            adapter = MainItemAdapter(this, model.items) { item, _ ->
                item?.let {
                    val children = BusinessApi.getChildren(it)
                    if (children?.isNotEmpty() == true) {
                        ViewModelProviders.of(this).get(MainDataModel::class.java).items.value = children
                    }
                    else {
                        item.path?.let { path ->
                            ARouter.getInstance().build(path).navigation()
                        }
                    }
                }
            }
            connect(adapter, model.items)
            binding.rvList.adapter = adapter
        }
    }

    override fun onBackPressed() {
        val pageUp = BusinessApi.getChildren(model.items[0])
        if (pageUp?.isNotEmpty() == true) {
            ViewModelProviders.of(this).get(MainDataModel::class.java).items.value = pageUp
            return
        }
        super.onBackPressed()
    }
}
