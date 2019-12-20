package com.example.myjetpackapplication.single

import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import me.jessyan.autosize.AutoSizeConfig

/**
 * Created by liutiantian on 2019-12-20 09:48 星期五
 */
open class SingleRunApplication : MultiDexApplication() {

    protected fun initAutoSize() {
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

    protected fun initARouter(debug: Boolean) {
        if (debug) {
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
    }

    protected fun initLogger(debug: Boolean) {
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                if (debug) {
                    return super.isLoggable(priority, tag)
                }
                return priority >= Logger.ERROR
            }
        })
    }
}