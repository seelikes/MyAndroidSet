package com.github.seelikes.android.media.data

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.github.seelikes.android.media.MediaUtil

/**
 * Created by liutiantian on 2020-01-18 11:52 星期六
 */
class MediaInfoDataSource(private val context: Context, private val uri: Uri, private val pageSize: Int = 20) : PageKeyedDataSource<Long, MediaInfo>() {
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, MediaInfo>) {
        var nextKey: Long? = (params.requestedLoadSize / pageSize + (if (params.requestedLoadSize % pageSize != 0) 1 else 0)).toLong()
        val list = ArrayList<MediaInfo>()
        MediaUtil.queryMedia(context, uri, 0, params.requestedLoadSize.toLong())?.toCollection(list)
        Log.i("MediaInfoDataSource", "MediaInfoDataSource, params.key: ${params.requestedLoadSize }}; list.size: ${list.size}")
        if (list.size < params.requestedLoadSize) {
            nextKey = null
        }
        callback.onResult(list, null, nextKey)
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, MediaInfo>) {
        val list = ArrayList<MediaInfo>()
        MediaUtil.queryMedia(context, uri, params.key * pageSize, pageSize.toLong())?.toCollection(list)
        Log.i("MediaInfoDataSource", "loadAfter, params.key: ${params.key }}; list.size: ${list.size}")
        callback.onResult(list,  if (list.size >= pageSize) params.key + 1 else null)
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, MediaInfo>) {
        val list = ArrayList<MediaInfo>()
        MediaUtil.queryMedia(context, uri, (params.key - 1) * pageSize, pageSize.toLong())?.toCollection(list)
        Log.i("MediaInfoDataSource", "loadBefore, params.key: ${params.key }}; list.size: ${list.size}")
        callback.onResult(list, if (list.size >= pageSize && params.key - 1 > 0) params.key - 1 else null)
    }
}