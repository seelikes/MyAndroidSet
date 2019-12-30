package com.example.myjetpackapplication.business.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem

class SingleNavigationDataModel : ViewModel() {
    val items: MutableLiveData<List<BusinessItem>> by lazy { MutableLiveData<List<BusinessItem>>() }
}