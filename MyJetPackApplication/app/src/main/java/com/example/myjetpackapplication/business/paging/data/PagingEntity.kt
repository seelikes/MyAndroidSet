package com.example.myjetpackapplication.business.paging.data

import java.io.Serializable

/**
 * Created by liutiantian on 2019-05-14 11:24 星期二
 */
data class PagingEntity(
        /**
         * 编号
         */
        var id: Long = 0,

        /**
         * 存储内容
         */
        var paging: String = "",

        /**
         * 创建时间
         */
        var time: Long = 0
) : Serializable