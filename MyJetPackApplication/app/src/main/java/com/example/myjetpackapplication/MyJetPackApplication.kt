package com.example.myjetpackapplication

import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import me.jessyan.autosize.AutoSizeConfig

/**
 * Created by liutiantian on 2019-05-02 11:36 星期四
 */
class MyJetPackApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        initLogger()
        initAutoSize()
        initARouter()
    }

    private fun initAutoSize() {
        with(AutoSizeConfig.getInstance()) {
            isBaseOnWidth = true
            isUseDeviceSize = true
            designWidthInDp = 1080
            designHeightInDp = 1920
            with(unitsManager) {
                isSupportDP = false
                isSupportSP = false
            }
        }
    }

    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
    }

    private fun initLogger() {
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                if (BuildConfig.DEBUG) {
                    return super.isLoggable(priority, tag)
                }
                return priority >= Logger.ERROR
            }
        })
    }
}