package com.example.myjetpackapplication.business.profiler.cpu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem

class SingleProfilerCpuDataModel : ViewModel() {
    val items: MutableLiveData<List<BusinessItem>> by lazy { MutableLiveData<List<BusinessItem>>() }
}