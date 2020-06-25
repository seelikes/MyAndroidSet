package com.github.seelikes.android.plugin.library.one.impl

import android.content.Context
import android.widget.Toast
import com.github.seelikes.android.plugin.connector.annotation.Connector
import com.github.seelikes.android.plugin.library.one.api.OneApi

@Connector(OneApi::class)
class OneImpl : OneApi {
    override fun showOne(context: Context) {
        Toast.makeText(context, "showOne impl", Toast.LENGTH_LONG).show()
    }
}