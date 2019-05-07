package com.example.myjetpackapplication.business.main

/**
 * Created by liutiantian on 2019-05-02 12:14 星期四
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
@MustBeDocumented
annotation class MainItemBean(val title: Int, val path: String, val enable: Boolean, val children: Array<MainItemBean> = [])