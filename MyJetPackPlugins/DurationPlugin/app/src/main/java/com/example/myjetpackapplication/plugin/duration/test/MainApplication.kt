package com.example.myjetpackapplication.plugin.duration.test

import androidx.multidex.MultiDexApplication
import com.example.myjetpackapplication.single.SingleRunApplication

/**
 * Created by liutiantian on 2020-02-06 15:07 星期四
 */
class MainApplication : SingleRunApplication() {
    override fun onCreate() {
        super.onCreate()
        initARouter(BuildConfig.DEBUG)
        initAutoSize()
        initFresco()
        initLogger(BuildConfig.DEBUG)
    }
}