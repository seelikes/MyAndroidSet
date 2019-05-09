package com.example.myjetpackapplication.business.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myjetpackapplication.business.main.data.MainItemBean

class MainDataModel : ViewModel() {
    val items: MutableLiveData<Array<MainItemBean>> by lazy { MutableLiveData<Array<MainItemBean>>() }
}