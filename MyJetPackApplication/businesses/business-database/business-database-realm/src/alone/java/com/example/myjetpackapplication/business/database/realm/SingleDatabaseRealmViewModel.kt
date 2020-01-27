package com.example.myjetpackapplication.business.database.realm

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.database.realm.R
import com.example.myjetpackapplication.business.database.realm.databinding.ActivitySingleDatabaseRealmBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SingleDatabaseRealmViewModel(
    host: SingleDatabaseRealmActivity,
    binding: ActivitySingleDatabaseRealmBinding
) : BasicHostViewModel<SingleDatabaseRealmViewModel, SingleDatabaseRealmActivity, ActivitySingleDatabaseRealmBinding>(
    host,
    binding
) {
    val title = ObservableInt(R.string.single_database_realm_title)
}