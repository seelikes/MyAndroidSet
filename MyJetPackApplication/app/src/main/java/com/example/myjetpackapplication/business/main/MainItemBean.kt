package com.example.myjetpackapplication.business.main

/**
 * Created by liutiantian on 2019-05-02 12:14 星期四
 */
class MainItemBean(val title: Int, val path: String, val enable: Boolean = true, val children: Array<MainItemBean> = arrayOf())