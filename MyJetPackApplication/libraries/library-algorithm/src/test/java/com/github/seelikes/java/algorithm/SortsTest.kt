package com.github.seelikes.java.algorithm

import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by liutiantian on 2019-12-28 14:39 星期六
 */
class SortsTest {
    private val arr = arrayOf(8, 1, 7, 6, 4)

    @Test
    fun bubbleSort() {
        val paramArr = arrayOf(*arr)
        Sorts.bubbleSort(paramArr) { a, b -> a - b}
        Assert.assertArrayEquals(arrayOf(1, 4, 6, 7, 8), paramArr)

        val paramList = mutableListOf(*arr)
        Sorts.bubbleSort(paramList) { a, b -> a - b}
        Assert.assertArrayEquals(arrayOf(1, 4, 6, 7, 8), paramList.toTypedArray())
    }

    @Test
    fun selectSort() {
        val paramArr = arrayOf(*arr)
        Sorts.selectSort(paramArr) { a, b -> a - b}
        Assert.assertArrayEquals(arrayOf(1, 4, 6, 7, 8), paramArr)

        val paramList = mutableListOf(*arr)
        Sorts.selectSort(paramList) { a, b -> a - b}
        Assert.assertArrayEquals(arrayOf(1, 4, 6, 7, 8), paramList.toTypedArray())
    }

    @Test
    fun insertSort() {
        val paramArr = arrayOf(*arr)
        Sorts.insertSort(paramArr) { a, b -> a - b}
        Assert.assertArrayEquals(arrayOf(1, 4, 6, 7, 8), paramArr)

        val paramList = mutableListOf(*arr)
        Sorts.insertSort(paramList) { a, b -> a - b}
        Assert.assertArrayEquals(arrayOf(1, 4, 6, 7, 8), paramList.toTypedArray())
    }

    @Test
    fun shellSort() {
        val paramArr = arrayOf(*arr)
        Sorts.shellSort(paramArr) { a, b -> a - b}
        Assert.assertArrayEquals(arrayOf(1, 4, 6, 7, 8), paramArr)

        val paramList = mutableListOf(*arr)
        Sorts.shellSort(paramList) { a, b -> a - b}
        Assert.assertArrayEquals(arrayOf(1, 4, 6, 7, 8), paramList.toTypedArray())
    }

    @Test
    fun mergeSort() {
        val paramArr = arrayOf(*arr)
        Sorts.mergeSort(paramArr) { a, b -> a - b}
        Assert.assertArrayEquals(arrayOf(1, 4, 6, 7, 8), paramArr)

        val paramList = mutableListOf(*arr)
        Sorts.mergeSort(paramList) { a, b -> a - b}
        Assert.assertArrayEquals(arrayOf(1, 4, 6, 7, 8), paramList.toTypedArray())
    }

    @Test
    fun quickSort() {
        val paramArr = arrayOf(*arr)
        Sorts.quickSort(paramArr, { a, b -> a - b})
        Assert.assertArrayEquals(arrayOf(1, 4, 6, 7, 8), paramArr)

        val paramList = mutableListOf(*arr)
        Sorts.quickSort(paramList, { a, b -> a - b})
        Assert.assertArrayEquals(arrayOf(1, 4, 6, 7, 8), paramList.toTypedArray())
    }

    @Test
    fun heapSort() {
        val paramArr = arrayOf(*arr)
        Sorts.heapSort(paramArr) { a, b -> a - b}
        Assert.assertArrayEquals(arrayOf(1, 4, 6, 7, 8), paramArr)

        val paramList = mutableListOf(*arr)
        Sorts.heapSort(paramList) { a, b -> a - b}
        Assert.assertArrayEquals(arrayOf(1, 4, 6, 7, 8), paramList.toTypedArray())
    }

    @Test
    fun countSort() {
        val paramArr = arrayOf(*arr)
        Sorts.countSort(paramArr, TreeMap<Int, Int?> { a, b -> a - b})
        Assert.assertArrayEquals(arrayOf(1, 4, 6, 7, 8), paramArr)

        val paramList = mutableListOf(*arr)
        Sorts.countSort(paramList, TreeMap<Int, Int?> { a, b -> a - b})
        Assert.assertArrayEquals(arrayOf(1, 4, 6, 7, 8), paramList.toTypedArray())
    }
}