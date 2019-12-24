package com.example.myjetpackapplication.business.database

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.database.R
import com.example.myjetpackapplication.business.database.databinding.ActivityDatabaseBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(title = "database_title")
class DatabaseActivity : BasicActivity<DatabaseActivity, DatabaseViewModel, ActivityDatabaseBinding>() {
    override fun initModel(savedInstanceState: Bundle?): DatabaseViewModel = DatabaseViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_database))
}