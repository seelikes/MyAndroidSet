package com.example.myjetpackapplication.business.setting

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.setting.R
import com.example.myjetpackapplication.business.setting.databinding.ActivitySettingBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SettingViewModel(host: SettingActivity, binding: ActivitySettingBinding) :
    BasicHostViewModel<SettingViewModel, SettingActivity, ActivitySettingBinding>(host, binding) {
    val title = ObservableInt(R.string.setting_title)
}