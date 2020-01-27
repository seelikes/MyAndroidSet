package com.example.myjetpackapplication.business.database.realm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem

class SingleDatabaseRealmDataModel : ViewModel() {
    val items: MutableLiveData<List<BusinessItem>> by lazy { MutableLiveData<List<BusinessItem>>() }
}