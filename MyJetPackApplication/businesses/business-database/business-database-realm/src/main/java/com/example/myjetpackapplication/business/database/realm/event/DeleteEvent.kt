package com.example.myjetpackapplication.business.database.realm.event

import com.example.myjetpackapplication.business.database.realm.data.DatabaseRealmBean
import com.example.myjetpackapplication.business.database.realm.data.DatabaseRealmEntity

/**
 * Created by liutiantian on 2020-01-27 11:52 星期一
 */
data class DeleteEvent (
    val bean: DatabaseRealmBean
)