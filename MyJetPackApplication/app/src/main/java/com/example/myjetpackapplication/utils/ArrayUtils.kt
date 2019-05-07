package com.example.myjetpackapplication.utils

import kotlin.math.min

/**
 * Created by liutiantian on 2019-05-06 08:35 星期一
 */
fun <E> mergeArray(dest: ArrayList<E>, source: Array<E>) {
    val size = min(dest.size, source.size)
    for (i in 0 until size) {
        if (dest[i] != source[i]) {
            dest[i] = source[i]
        }
    }
    for (i in (size until dest.size).reversed()) {
        dest.removeAt(i)
    }
    for (i in size until source.size) {
        dest.add(source[i])
    }
}