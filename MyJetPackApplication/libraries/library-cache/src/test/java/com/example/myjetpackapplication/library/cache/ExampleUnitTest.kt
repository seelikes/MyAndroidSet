package com.example.myjetpackapplication.library.cache

import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun iteratorRemove() {
        val map = mutableMapOf(
            1 to 2,
            3 to 4,
            5 to 6
        )
        val iterator = map.iterator()
        for (entry in iterator) {
            iterator.remove()
        }
    }
}