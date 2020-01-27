package com.example.myjetpackapplication.business.database.realm

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.database.realm.data.DatabaseRealmBean
import com.example.myjetpackapplication.business.database.realm.databinding.ActivityDatabaseRealmBinding
import com.example.myjetpackapplication.business.database.realm.event.DeleteEvent
import com.github.seelikes.android.mvvm.basic.BasicActivity
import com.java.lib.oil.lang.StringManager
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.random.Random

@Business(title = "database_realm_title")
@Route(path = "/business/database_realm")
class DatabaseRealmActivity : BasicActivity<DatabaseRealmActivity, DatabaseRealmViewModel, ActivityDatabaseRealmBinding>() {
    override fun initModel(savedInstanceState: Bundle?): DatabaseRealmViewModel = DatabaseRealmViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_database_realm))

    override fun initView(binding: ActivityDatabaseRealmBinding) {
        super.initView(binding)
        initRecycler(binding.rvList)
    }

    private fun initRecycler(recycler: RecyclerView) {
        if (recycler.layoutManager == null) {
            recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
        if (recycler.itemDecorationCount == 0) {
            recycler.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    if (parent.getChildAdapterPosition(view) != 0) {
                        outRect.top = 4
                    }
                }
            })
        }
        if (recycler.adapter !is DatabaseRealmAdapter) {
            recycler.adapter = DatabaseRealmAdapter(this, activityScope)
        }
    }

    fun onClickAddOne() {
        Logger.i("202001271811, enter")
        activityScope.launch {
            Logger.i("202001271811, coroutine enter")
            val realmBean = DatabaseRealmBean()
            realmBean.key = StringManager.getInstance().hex(Random.nextBytes(2))
            realmBean.value = StringManager.getInstance().hex(Random.nextBytes(4))
            ViewModelProvider(this@DatabaseRealmActivity, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[DatabaseRealmDataModel::class.java].add(realmBean)
        }
    }

    fun onClickRandomGenerate() {
        activityScope.launch {
            for (i in 0..128) {
                onClickAddOne()
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onClickDelete(event: DeleteEvent) {
        activityScope.launch {
            ViewModelProvider(this@DatabaseRealmActivity, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[DatabaseRealmDataModel::class.java].delete(event.bean)
        }
    }

    fun onClickClear() {
        activityScope.launch {
            ViewModelProvider(this@DatabaseRealmActivity, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[DatabaseRealmDataModel::class.java].delete()
        }
    }
}