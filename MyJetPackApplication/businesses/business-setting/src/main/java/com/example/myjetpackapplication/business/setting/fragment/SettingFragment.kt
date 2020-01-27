package com.example.myjetpackapplication.business.setting.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.myjetpackapplication.business.setting.R

/**
 * Created by liutiantian on 2019-06-03 20:22 星期一
 */
class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_setting, rootKey)
    }

}