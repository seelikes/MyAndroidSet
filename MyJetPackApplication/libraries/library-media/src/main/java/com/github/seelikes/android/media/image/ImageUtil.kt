package com.github.seelikes.android.media.image

import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.lang.IllegalArgumentException

/**
 * Created by liutiantian on 2020-01-19 15:37 星期日
 */
object ImageUtil {
    /**
     * 将位图写入文件
     */
    fun writeBitmapToFile(bitmap: Bitmap?, file: File, format: Bitmap.CompressFormat = Bitmap.CompressFormat.WEBP, quality: Int = 83): Boolean {
        bitmap ?: throw IllegalArgumentException("bitmap can not be null")
        if (!file.parentFile.exists()) {
            if (!file.parentFile.mkdirs()) {
                throw IllegalStateException("can not create parent file as path: ${file.parentFile.absolutePath}")
            }
        }
        val os = FileOutputStream(file)
        os.use {
            return bitmap.compress(format, quality, it)
        }
    }
}