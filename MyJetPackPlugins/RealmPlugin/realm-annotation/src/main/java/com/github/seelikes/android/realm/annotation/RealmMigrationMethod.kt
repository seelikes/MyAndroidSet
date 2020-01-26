package com.github.seelikes.android.realm.annotation

/**
 * Created by liutiantian on 2020-01-23 13:31 星期四
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
annotation class RealmMigrationMethod (
    /**
     * 接受执行的版本号
     */
    val oldVersion: Int,

    /**
     * 执行完毕之后的版本号
     */
    val newVersion: Int
)