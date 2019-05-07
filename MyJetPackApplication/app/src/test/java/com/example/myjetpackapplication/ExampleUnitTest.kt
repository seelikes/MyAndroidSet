package com.example.myjetpackapplication

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun downTo_to() {
        var sum = 0
        for (i in 10 downTo 0) {
            sum += 1
        }
        assertEquals(11, sum)
    }
}
