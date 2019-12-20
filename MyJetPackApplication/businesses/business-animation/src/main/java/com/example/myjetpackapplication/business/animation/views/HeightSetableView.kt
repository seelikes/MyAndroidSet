package com.example.myjetpackapplication.business.animation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.orhanobut.logger.Logger

/**
 * Created by liutiantian on 2019-12-20 10:51 星期五
 */
class HeightSetableView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    fun setHeight(height: Float) {
        Logger.i("height: $height")
        if (layoutParams.height !=  height.toInt()) {
            layoutParams.height = height.toInt()
            layoutParams = layoutParams
        }
    }
}