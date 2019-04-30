package com.example.myjetpackapplication.business.main

import android.util.Base64
import androidx.databinding.ObservableField
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myjetpackapplication.basic.BasicHostViewModel
import com.example.myjetpackapplication.databinding.ActivityMainBinding
import java.security.SecureRandom

class MainViewModel(host: MainActivity, binding: ActivityMainBinding) : BasicHostViewModel<MainViewModel, MainActivity, ActivityMainBinding>(host, binding) {
    val helloMa = ObservableField<String>()

    init {
        ViewModelProviders.of(host).get(MainDataModel::class.java).hello.observe(host, Observer<String> {
            helloMa.set(it)
        })
    }

    fun onUiClickHello() {
        ViewModelProviders.of(host).get(MainDataModel::class.java).hello.value = Base64.encodeToString(SecureRandom().generateSeed(8), 0)
    }
}