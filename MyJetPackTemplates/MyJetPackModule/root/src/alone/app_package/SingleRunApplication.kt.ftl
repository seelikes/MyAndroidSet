package ${packageName}

import com.example.myjetpackapplication.single.SingleRunApplication

/**
 * Created by liutiantian on 2019-12-20 10:26 星期五
 */
class ${underscoreToCamelCase(activitySingleLayout)}Application : SingleRunApplication() {
    override fun onCreate() {
        super.onCreate()
        initLogger(BuildConfig.DEBUG)
        initAutoSize()
        initARouter(BuildConfig.DEBUG)
        initFresco()
    }
}