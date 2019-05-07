package com.example.myjetpackapplication.business.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainDataModel : ViewModel() {
    val items: MutableLiveData<Array<MainItemBean>> by lazy { MutableLiveData<Array<MainItemBean>>() }
}