package com.example.myjetpackapplication.business.main

import android.util.Base64
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.myjetpackapplication.basic.BasicHostViewModel
import com.example.myjetpackapplication.databinding.ActivityMainBinding
import java.security.SecureRandom

class MainViewModel(host: MainActivity, binding: ActivityMainBinding) : BasicHostViewModel<MainViewModel, MainActivity, ActivityMainBinding>(host, binding) {
    private val hello = MutableLiveData<String>("XXXXXXXXXX")
    val helloMa = ObservableField<String>(hello.value)

    init {
        hello.observe(host, Observer<String> {
            helloMa.set(it)
        })
    }

    fun onUiClickHello() {
        Log.e("XXX1111", "onUiClickHello.UL1200LP.DI1211, enter")
        hello.value = Base64.encodeToString(SecureRandom().generateSeed(8), 0)
    }
}