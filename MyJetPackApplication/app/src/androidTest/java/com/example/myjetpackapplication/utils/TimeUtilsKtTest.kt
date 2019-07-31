package com.example.myjetpackapplication.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.java.lib.oil.lang.CalendarManager
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by liutiantian on 2019-07-2019/7/31 08:50 星期三
 */
@RunWith(AndroidJUnit4::class)
class TimeUtilsKtTest {

    @Test
    fun format() {
        val date = CalendarManager.getInstance().parse("2016-10-20 08:10:11", "yyyy-MM-dd HH:mm:ss")
        Assert.assertNotNull(date)
        val dateStr = format(date)
        Assert.assertNotNull(dateStr)
        Assert.assertEquals("2016-10-20 08:10:11", dateStr)
    }
}