package com.github.seelikes.android.duration.api

/**
 * Created by liutiantian on 2020-02-07 19:01 星期五
 */
data class DurationReportGroup (
    /**
     * 分组标题
     */
    val title: String = "",

    /**
     * 报告清单
     */
    val reports: List<DurationReport>? = null,

    /**
     * 记录清单
     */
    val durations: List<Duration>? = null
)