package com.example.myjetpackapplication.business.database.realm

import androidx.databinding.ObservableInt
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myjetpackapplication.business.database.realm.R
import com.example.myjetpackapplication.business.database.realm.databinding.ActivityDatabaseRealmBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class DatabaseRealmViewModel(host: DatabaseRealmActivity, binding: ActivityDatabaseRealmBinding) : BasicHostViewModel<DatabaseRealmViewModel, DatabaseRealmActivity, ActivityDatabaseRealmBinding>(host, binding) {
    val title = ObservableInt(R.string.database_realm_title)

    init {
        ViewModelProvider(host, ViewModelProvider.AndroidViewModelFactory.getInstance(host.application))[DatabaseRealmDataModel::class.java].list.observe(host, Observer {
            (binding.rvList.adapter as? DatabaseRealmAdapter)?.submitList(it)
        })
    }
}