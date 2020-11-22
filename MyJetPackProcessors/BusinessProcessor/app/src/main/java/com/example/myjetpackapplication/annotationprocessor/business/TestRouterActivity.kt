package com.example.myjetpackapplication.annotationprocessor.business

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business

@Business(title = "test_router", path = "/business/test/router")
class TestRouterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_router)
    }
}
