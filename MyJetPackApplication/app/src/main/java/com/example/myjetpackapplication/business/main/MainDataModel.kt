package com.example.myjetpackapplication.business.main

import android.util.Base64
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.security.SecureRandom

class MainDataModel : ViewModel() {
    val hello: MutableLiveData<String> by lazy { MutableLiveData<String>(Base64.encodeToString(SecureRandom().generateSeed(8), 0)) }
}