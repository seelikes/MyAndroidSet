package com.example.myjetpackapplication.business.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.R
import com.example.myjetpackapplication.basic.BasicActivity
import com.example.myjetpackapplication.databinding.ActivityMainBinding

@Route(path = "/main")
class  MainActivity : BasicActivity<MainActivity, MainViewModel, ActivityMainBinding>() {
    override fun initModel(savedInstanceState: Bundle?): MainViewModel = MainViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_main))
}
