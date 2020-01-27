package com.example.myjetpackapplication.business.database.realm.data

import java.util.*

/**
 * Created by liutiantian on 2020-01-27 20:49 星期一
 */
data class DatabaseRealmBean (
    var id: Long? = null,
    var key: String? = null,
    var value: String? = null,
    var createTime: Date = Date(),
    var updateTime: Date? = null
)