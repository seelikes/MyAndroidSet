package com.github.seelikes.android.plugin.library.obj.impl

import android.content.Context
import android.widget.Toast
import com.github.seelikes.android.plugin.connector.annotation.Connector
import com.github.seelikes.android.plugin.library.obj.api.ObjApi

/**
 * Created by liutiantian on 2020-06-29
 * Email: liutiantian01@corp.netease.com
 */
@Connector(ObjApi::class)
object ObjImpl : ObjApi {
    override fun dumpHashCode(context: Context) {
        Toast.makeText(context, "ObjApi, hashCode: ${hashCode()}", Toast.LENGTH_LONG).show()
    }
}