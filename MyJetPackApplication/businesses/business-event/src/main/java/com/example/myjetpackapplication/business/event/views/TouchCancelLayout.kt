package com.example.myjetpackapplication.business.event.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import com.orhanobut.logger.Logger

/**
 * Created by liutiantian on 2019-12-22 16:18 星期日
 */
class TouchCancelLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Logger.i("event.action: ${event?.action}")
        return super.onTouchEvent(event)
    }
}