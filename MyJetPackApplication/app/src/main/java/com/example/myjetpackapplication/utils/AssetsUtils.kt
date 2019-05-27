package com.example.myjetpackapplication.utils

import android.content.Context
import com.java.lib.oil.file.FileUtils
import java.io.File
import java.io.FileOutputStream

/**
 * Created by liutiantian on 2019-05-27 17:44 星期一
 */

fun copyAssets(context: Context, path: String): String {
    val input = context.assets.open(path)
    val targetFile = File(context.cacheDir, "${path.hashCode()}${path.substring(path.lastIndexOf("."))}")
    val output = FileOutputStream(targetFile)
    FileUtils.getInstance().writeStreamToStream(input, output)
    return targetFile.absolutePath
}