package com.github.seelikes.android.media.data

import java.util.*

/**
 * Created by liutiantian on 2020-01-17 22:22 星期五
 */
data class MediaInfo (
    /**
     * 编号
     */
    var id: Long? = null,

    /**
     * 数据载体
     */
    var data: String? = null,

    /**
     * 大小
     */
    var size: Long? = null,

    /**
     * 显示名称
     */
    var displayName: String? = null,

    /**
     * 标题
     */
    var title: String? = null,

    /**
     * 创建日期
     */
    var dateAdded: Date? = null,

    /**
     * 更新日期
     */
    var dateModified: Date? = null,

    /**
     * 文件类型
     */
    var mimeType: String? = null,

    /**
     * 宽度
     */
    var width: Int? = null,

    /**
     * 高度
     */
    var height: Int? = null
)