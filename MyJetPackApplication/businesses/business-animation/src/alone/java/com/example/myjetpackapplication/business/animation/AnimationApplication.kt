package com.example.myjetpackapplication.business.animation

import com.example.myjetpackapplication.single.SingleRunApplication

/**
 * Created by liutiantian on 2019-12-20 10:26 星期五
 */
class AnimationApplication : SingleRunApplication() {
    override fun onCreate() {
        super.onCreate()
        initLogger(BuildConfig.DEBUG)
        initAutoSize()
        initARouter(BuildConfig.DEBUG)
    }
}