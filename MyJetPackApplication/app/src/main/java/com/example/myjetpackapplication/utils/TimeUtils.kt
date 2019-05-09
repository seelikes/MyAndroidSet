package com.example.myjetpackapplication.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by liutiantian on 2019-05-09 14:03 星期四
 */
fun format(date: Date, format: String = "yyyy-MM-dd HH:mm:ss") = SimpleDateFormat(format, Locale.CHINA).format(date)