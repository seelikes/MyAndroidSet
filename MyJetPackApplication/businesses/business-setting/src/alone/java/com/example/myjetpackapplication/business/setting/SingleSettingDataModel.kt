package com.example.myjetpackapplication.business.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem

class SingleSettingDataModel : ViewModel() {
    val items: MutableLiveData<List<BusinessItem>> by lazy { MutableLiveData<List<BusinessItem>>() }
}