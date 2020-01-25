package com.example.myjetpackapplication.plugin.realm.app;

import android.util.Log;

import com.github.seelikes.android.realm.RealmMigrationClass;
import com.github.seelikes.android.realm.RealmMigrationMethod;

import io.realm.DynamicRealm;

/**
 * Created by liutiantian on 2020-01-23 15:33 星期四
 */
@RealmMigrationClass
public class MainRealmMigration {
    @RealmMigrationMethod(oldVersion = 0, newVersion = 1)
    void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        Log.i("MainRealmMigration", "migrate, enter");
    }
}
