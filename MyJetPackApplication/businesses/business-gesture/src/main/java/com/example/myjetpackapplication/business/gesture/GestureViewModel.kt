package com.example.myjetpackapplication.business.gesture

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myjetpackapplication.business.gesture.R
import com.example.myjetpackapplication.business.gesture.databinding.ActivityGestureBinding
import com.example.myjetpackapplication.business.gesture.info.GestureInfo
import com.github.seelikes.android.mvvm.basic.BasicHostViewModel

class GestureViewModel(host: GestureActivity, binding: ActivityGestureBinding) : BasicHostViewModel<GestureViewModel, GestureActivity, ActivityGestureBinding>(host, binding) {
    val title = ObservableInt(R.string.gesture_title)
    val history = ObservableField<String>()

    init {
        ViewModelProviders.of(host).get(GestureDataModel::class.java).history.observe(host, Observer<List<GestureInfo>> {
            history.set(it.joinToString(
                separator = "\n",
                transform = { gestureInfo ->
                    String.format("%s %s", gestureInfo.time, gestureInfo.gesture)
                }
            ))
        })
    }
}