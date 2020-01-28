package com.github.seelikes.android.cache

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 * Created by liutiantian on 2020-01-28 23:25 星期二
 */
class CacheProvider : ContentProvider() {
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("insert is not supported")
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        throw UnsupportedOperationException("insert is not supported")
    }

    override fun onCreate(): Boolean {
        Cache.init()
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("insert is not supported")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("insert is not supported")
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}