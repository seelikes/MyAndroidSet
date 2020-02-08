package com.example.myjetpackapplication.plugin.duration.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onHelloClick(view: View) {
        ARouter.getInstance().build("/api/duration").navigation()
    }
}
