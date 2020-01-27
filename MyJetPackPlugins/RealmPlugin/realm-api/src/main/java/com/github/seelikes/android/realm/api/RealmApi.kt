package com.github.seelikes.android.realm.api

import android.app.Application
import android.content.Context
import io.realm.Realm
import java.lang.ref.WeakReference

/**
 * Created by liutiantian on 2020-01-22 16:39 星期三
 */
internal lateinit var weakContext: WeakReference<Context>

internal fun init(context: Context?) {
    context?.let {
        weakContext = WeakReference(it)
        if (it is Application) {
            Realm.init(it)
        }
        else {
            Realm.init(it.applicationContext)
        }
    }
}

private val realmLocal: ThreadLocal<Realm> = ThreadLocal()

val Any?.realm: Realm
    get() {
        throw UnsupportedOperationException("you must apply realm-helper to get this worked")
    }