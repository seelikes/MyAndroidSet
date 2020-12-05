package com.example.myjetpackapplication.business.gesture

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business
import com.example.myjetpackapplication.business.gesture.databinding.ActivityGestureBinding
import com.example.myjetpackapplication.business.gesture.info.GestureInfo
import com.github.seelikes.android.mvvm.basic.BasicActivity
import com.orhanobut.logger.Logger
import java.text.SimpleDateFormat
import java.util.*

@Business(path = "/business/gesture", title = "gesture_title")
class GestureActivity : BasicActivity<GestureActivity, GestureViewModel, ActivityGestureBinding>() {
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gestureDetector = GestureDetector(this, object : GestureDetector.OnGestureListener {
            val formatter = SimpleDateFormat("HH:mm:ss SSS", Locale.US)

            override fun onShowPress(e: MotionEvent?) {
                Toast.makeText(this@GestureActivity, "onShowPress", Toast.LENGTH_LONG).show()
                Logger.i("onShowPress")
                onNewEvent(formatter.format(Date()), "onShowPress")
            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                Toast.makeText(this@GestureActivity, "onSingleTapUp", Toast.LENGTH_LONG).show()
                Logger.i("onSingleTapUp")
                onNewEvent(formatter.format(Date()), "onSingleTapUp")
                return true
            }

            override fun onDown(e: MotionEvent?): Boolean {
                Toast.makeText(this@GestureActivity, "onDown", Toast.LENGTH_LONG).show()
                Logger.i("onDown")
                onNewEvent(formatter.format(Date()), "onDown")
                return true
            }

            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                Toast.makeText(this@GestureActivity, "onFling", Toast.LENGTH_LONG).show()
                Logger.i("onFling")
                onNewEvent(formatter.format(Date()), "onFling")
                return true
            }

            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                Toast.makeText(this@GestureActivity, "onScroll", Toast.LENGTH_LONG).show()
                Logger.i("onScroll")
                onNewEvent(formatter.format(Date()), "onScroll")
                return true
            }

            override fun onLongPress(e: MotionEvent?) {
                Toast.makeText(this@GestureActivity, "onLongPress", Toast.LENGTH_LONG).show()
                Logger.i("onLongPress")
                onNewEvent(formatter.format(Date()), "onLongPress")
            }

            private fun onNewEvent(time: String, gesture: String) {
                val history = mutableListOf<GestureInfo>()
                if (gesture != "onDown") {
                    val oldHistory = ViewModelProviders.of(this@GestureActivity).get(GestureDataModel::class.java).history.value
                    oldHistory?.let {
                        for (gestureHistory in it) {
                            history.add(gestureHistory)
                        }
                    }
                }
                history.add(GestureInfo(time, gesture))
                ViewModelProviders.of(this@GestureActivity).get(GestureDataModel::class.java).history.postValue(history)
            }
        })
    }

    override fun initModel(savedInstanceState: Bundle?): GestureViewModel = GestureViewModel(this, DataBindingUtil.setContentView(this, R.layout.activity_gesture))

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }
}