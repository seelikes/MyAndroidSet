package com.github.seelikes.android.media.video

import android.graphics.Bitmap
import android.media.ThumbnailUtils

/**
 * Created by liutiantian on 2020-01-17 23:58 星期五
 */
object VideoUtil {
    /**
     * 获取视频文件缩略图
     * 海报
     */
    fun getThumbnail(videoPath: String, width: Int, height: Int, kind: Int): Bitmap? {
        val bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind)
        bitmap?.let {
            return ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT)
        }
        return null
    }
}