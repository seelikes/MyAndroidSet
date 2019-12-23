package com.example.myjetpackapplication.business.gesture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myjetpackapplication.annotationprocessor.businessannotationprocessor.annotation.BusinessItem
import com.example.myjetpackapplication.business.gesture.info.GestureInfo

class GestureDataModel : ViewModel() {
    val history: MutableLiveData<List<GestureInfo>> by lazy { MutableLiveData<List<GestureInfo>>() }
}