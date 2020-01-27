package com.example.myjetpackapplication.business.database.realm.data

import com.java.lib.oil.json.JSON
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

/**
 * Created by liutiantian on 2020-01-26 17:36 星期日
 */
open class DatabaseRealmEntity : RealmObject() {
    /**
     * 编号
     */
    @PrimaryKey
    var id: Long? = null

    /**
     * 键
     */
    @Index
    @Required
    var key: String? = null

    /**
     * 值
     */
    @Required
    var value: String? = null

    /**
     * 创建时间
     */
    var createTime: Date = Date()

    /**
     * 更新时间
     */
    var updateTime: Date? = null

    override fun hashCode(): Int {
        return toString().hashCode()
    }

    override fun toString(): String {
        return JSON.toJsonString(this)
    }

    override fun equals(other: Any?): Boolean {
        if (other is DatabaseRealmEntity) {
            return other.id == this.id && other.key == this.key && other.value == this.value && other.createTime == this.createTime && other.updateTime == this.updateTime
        }
        return false
    }
}