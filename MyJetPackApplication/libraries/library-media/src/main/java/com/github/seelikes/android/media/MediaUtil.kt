package com.github.seelikes.android.media

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.github.seelikes.android.media.data.MediaInfo
import java.util.*

/**
 * Created by liutiantian on 2020-01-18 11:29 星期六
 */
object MediaUtil {
    /**
     * 查询媒体信息
     */
    fun queryMedia(context: Context, uri: Uri, offset: Long = 0, limit: Long = -1): List<MediaInfo>? {
        val cursor = context.contentResolver.query(uri, null, null, null, MediaStore.Video.DEFAULT_SORT_ORDER + (if (limit > 0) " limit $offset, $limit" else ""))
        val res = mutableListOf<MediaInfo>()
        cursor?.let {
            while (it.moveToNext()) {
                val media = MediaInfo()
                media.id = it.getLong(it.getColumnIndex(MediaStore.MediaColumns._ID))
                media.data = it.getString(it.getColumnIndex(MediaStore.MediaColumns.DATA))
                media.size = it.getLong(it.getColumnIndex(MediaStore.MediaColumns.SIZE))
                media.displayName = it.getString(it.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME))
                media.title = it.getString(it.getColumnIndex(MediaStore.MediaColumns.TITLE))
                media.dateAdded = Date(it.getLong(it.getColumnIndex(MediaStore.MediaColumns.DATE_ADDED)))
                media.dateModified = Date(it.getLong(it.getColumnIndex(MediaStore.MediaColumns.DATE_MODIFIED)))
                media.mimeType = it.getString(it.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    media.width = it.getInt(it.getColumnIndex(MediaStore.MediaColumns.WIDTH))
                    media.height = it.getInt(it.getColumnIndex(MediaStore.MediaColumns.HEIGHT))
                }
                res.add(media)
            }
        }
        cursor?.close()
        return if (res.isNotEmpty()) res else null
    }
}