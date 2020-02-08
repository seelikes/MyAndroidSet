package com.github.seelikes.android.duration.api

/**
 * Created by liutiantian on 2020-02-05 11:09 星期三
 */
data class Duration (
    val hostClass: String = "",
    val method: String = "",
    val startTime: Long = 0,
    val endTime: Long = 0
)

val Duration.duration: Long
    get() = this.endTime - this.startTime

