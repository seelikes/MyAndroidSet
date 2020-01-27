package com.github.seelikes.android.realm.api

import android.content.Context
import io.realm.DynamicRealm
import io.realm.RealmMigration

/**
 * Created by liutiantian on 2020-01-23 14:34 星期四
 */
class RealmLibraryMigration(val context: Context) : RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hashCode(): Int {
        return RealmLibraryMigration::class.java.canonicalName.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is RealmLibraryMigration
    }
}