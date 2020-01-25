package com.example.myjetpackapplication.plugin.realm.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getExternalCacheDir();
        getFilesDir();
        getCacheDir();

    }
}
