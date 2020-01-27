package com.example.myjetpackapplication.business.setting

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.setting.R
import com.example.myjetpackapplication.business.setting.databinding.ActivitySettingBinding
import com.github.seelikes.android.mvvm.basic.BasicActivity

@Business(title = "setting_title")
@Route(path = "/business/setting")
class SettingActivity : BasicActivity<SettingActivity, SettingViewModel, ActivitySettingBinding>() {
    override fun initModel(savedInstanceState: Bundle?): SettingViewModel =
        SettingViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_setting))
}