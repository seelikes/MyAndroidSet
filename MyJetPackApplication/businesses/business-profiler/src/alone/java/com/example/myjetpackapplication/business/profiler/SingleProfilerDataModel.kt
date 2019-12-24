package com.example.myjetpackapplication.business.profiler

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem

class SingleProfilerDataModel : ViewModel() {
    val items: MutableLiveData<List<BusinessItem>> by lazy { MutableLiveData<List<BusinessItem>>() }
}