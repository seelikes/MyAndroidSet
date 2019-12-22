package com.example.myjetpackapplication.business.event.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.orhanobut.logger.Logger

/**
 * Created by liutiantian on 2019-12-22 16:26 星期日
 */
class TouchCancelView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Logger.i("event.action: ${event?.action}")
        return super.onTouchEvent(event)
    }
}