package com.example.myjetpackapplication

import com.example.myjetpackapplication.single.SingleRunApplication

/**
 * Created by liutiantian on 2019-05-02 11:36 星期四
 */
class MyJetPackApplication : SingleRunApplication() {
    override fun onCreate() {
        super.onCreate()
        initLogger(BuildConfig.DEBUG)
        initAutoSize()
        initARouter(BuildConfig.DEBUG)
        initFresco()
    }
}