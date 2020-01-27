package com.example.myjetpackapplication.business.setting

import androidx.databinding.ObservableInt
import com.example.myjetpackapplication.business.setting.R
import com.example.myjetpackapplication.business.setting.databinding.ActivitySingleSettingBinding
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class SingleSettingViewModel(host: SingleSettingActivity, binding: ActivitySingleSettingBinding) :
    BasicHostViewModel<SingleSettingViewModel, SingleSettingActivity, ActivitySingleSettingBinding>(
        host,
        binding
    ) {
    val title = ObservableInt(R.string.single_setting_title)
}