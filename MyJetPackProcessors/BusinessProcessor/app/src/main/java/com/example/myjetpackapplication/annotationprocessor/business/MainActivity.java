package com.example.myjetpackapplication.annotationprocessor.business;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myjetpackapplication.annotationprocessor.business.annotation.Business;
import com.example.myjetpackapplication.annotationprocessor.business.api.BusinessApi;

@Business(title = "app_name", path = "/business/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BusinessApi.INSTANCE.getChildren(null);
        findViewById(R.id.helloWorld).setOnClickListener(view -> {
            BusinessApi.INSTANCE.go(this, "/business/test/router");
        });
    }
}
