package com.example.myjetpackapplication.annotationprocessor.business.api

import com.example.myjetpackapplication.annotationprocessor.business.annotation.BusinessItem

/**
 * Created by liutiantian on 2019-12-23 14:07 星期一
 */
data class BusinessRecord (
    var versionName: String = "",
    var versionCode: Int = 0,
    var business: MutableList<BusinessItem>
)