package com.github.seelikes.android.plugin.connector.app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.github.seelikes.android.plugin.connector.api.ConnectorApi;
import com.github.seelikes.android.plugin.library.obj.api.ObjApi;
import com.github.seelikes.android.plugin.library.one.api.OneApi;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.apiOne).setOnClickListener(view -> {
            ConnectorApi.get(OneApi.class).showOne(this);
        });
        findViewById(R.id.apiObject).setOnClickListener(view -> {
            ConnectorApi.get(ObjApi.class).dumpHashCode(this);
        });
    }
}
