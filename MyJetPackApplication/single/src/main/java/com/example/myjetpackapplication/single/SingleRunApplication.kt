package com.example.myjetpackapplication.single

import androidx.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
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

    protected fun initFresco() {
        Fresco.initialize(this)
    }
}