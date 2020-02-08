package com.github.seelikes.android.duration.api

/**
 * Created by liutiantian on 2020-02-06 16:57 星期四
 */
data class DurationReport (
    /**
     * 方法所在类
     */
    val hostClass: String = "",

    /**
     * 方法名称
     */
    val method: String = "",

    /**
     * 检测到的调用次数
     */
    var count: Long = 0,

    /**
     * 调用总耗时
     */
    var totalDuration: Long = 0,

    /**
     * 最长调用时间
     */
    var longestDuration: Long = 0,

    /**
     * 最短调用时间
     */
    var shortestDuration: Long = -1,

    /**
     * 平均调用时间
     */
    var averageDuration: Long = 0
)