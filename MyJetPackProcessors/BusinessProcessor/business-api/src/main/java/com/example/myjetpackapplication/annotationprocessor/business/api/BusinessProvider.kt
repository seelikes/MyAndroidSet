package com.example.myjetpackapplication.annotationprocessor.business.api

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 * Created by liutiantian on 2019-12-23 13:23 星期一
 */
class BusinessProvider : ContentProvider() {
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("insert is not supported")
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        throw UnsupportedOperationException("insert is not supported")
    }

    override fun onCreate(): Boolean {
        BusinessApi.init(context!!)
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("insert is not supported")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("insert is not supported")
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}