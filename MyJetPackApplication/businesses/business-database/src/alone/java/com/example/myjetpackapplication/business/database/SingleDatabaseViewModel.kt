package com.example.myjetpackapplication.business.database

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.database.R
import com.example.myjetpackapplication.business.database.databinding.ActivitySingleDatabaseBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SingleDatabaseViewModel(
    host: SingleDatabaseActivity,
    binding: ActivitySingleDatabaseBinding
) : BasicHostViewModel<SingleDatabaseViewModel, SingleDatabaseActivity, ActivitySingleDatabaseBinding>(
    host,
    binding
) {
    val title = ObservableInt(R.string.single_database_title)
}