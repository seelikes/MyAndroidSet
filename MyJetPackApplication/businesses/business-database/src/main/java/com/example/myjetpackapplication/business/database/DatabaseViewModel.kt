package com.example.myjetpackapplication.business.database

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.database.R
import com.example.myjetpackapplication.business.database.databinding.ActivityDatabaseBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class DatabaseViewModel(host: DatabaseActivity, binding: ActivityDatabaseBinding) : BasicHostViewModel<DatabaseViewModel, DatabaseActivity, ActivityDatabaseBinding>(host, binding) {
    val title = ObservableInt(R.string.database_title)
}