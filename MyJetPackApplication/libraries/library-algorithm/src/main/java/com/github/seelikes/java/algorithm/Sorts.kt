@file:JvmName("Sorts")
package com.github.seelikes.java.algorithm

import java.util.*


/**
 * Created by liutiantian on 2019-12-28 14:38 星期六
 */
object Sorts {
    fun <T: Any> bubbleSort(arr: Array<T>, comparator: (T, T) -> Int) {
        for (i in arr.indices) {
            var min = arr[i]
            for (j in i + 1 until arr.size) {
                if (comparator.invoke(arr[j], min) < 0) {
                    arr[i] = arr[j]
                    arr[j] = min
                    min = arr[i]
                }
            }
        }
    }

    fun <T: Any> bubbleSort(list: MutableList<T>, comparator: (T, T) -> Int) {
        for (i in list.indices) {
            var min = list[i]
            for (j in i + 1 until list.size) {
                if (comparator.invoke(list[j], min) < 0) {
                    list[i] = list[j]
                    list[j] = min
                    min = list[i]
                }
            }
        }
    }

    fun <T: Any> selectSort(arr: Array<T>, comparator: (T, T) -> Int) {
        for (i in arr.indices) {
            var minIndex = i
            for (j in i + 1 until arr.size) {
                if (comparator.invoke(arr[j], arr[minIndex]) < 0) {
                    minIndex = j
                }
            }
            val tmp = arr[minIndex]
            arr[minIndex] = arr[i]
            arr[i] = tmp
        }
    }

    fun <T: Any> selectSort(list: MutableList<T>, comparator: (T, T) -> Int) {
        for (i in list.indices) {
            var minIndex = i
            for (j in i + 1 until list.size) {
                if (comparator.invoke(list[j], list[minIndex]) < 0) {
                    minIndex = j
                }
            }
            val tmp = list[minIndex]
            list[minIndex] = list[i]
            list[i] = tmp
        }
    }

    fun <T: Any> insertSort(arr: Array<T>, comparator: (T, T) -> Int) {
        for (i in 1 until arr.size) {
            val current = arr[i]
            var prev = i - 1
            while (prev >= 0 && comparator.invoke(arr[prev], current) > 0) {
                arr[prev + 1] = arr[prev]
                --prev
            }
            arr[prev + 1] = current
        }
    }

    fun <T: Any> insertSort(list: MutableList<T>, comparator: (T, T) -> Int) {
        for (i in 1 until list.size) {
            val current = list[i]
            var prev = i - 1
            while (prev >= 0 && comparator.invoke(list[prev], current) > 0) {
                list[prev + 1] = list[prev]
                --prev
            }
            list[prev + 1] = current
        }
    }

    fun <T: Any> shellSort(arr: Array<T>, comparator: (T, T) -> Int) {
        var gap: Int = arr.size / 3 + 1
        while (gap > 0) {
            var i = gap
            while (i < arr.size) {
                val current = arr[i]
                var prev = i - gap
                while (prev >= 0 && comparator.invoke(arr[prev], current) > 0) {
                    arr[prev + gap] = arr[prev]
                    prev -= gap
                }
                arr[prev + gap] = current
                i += gap
            }
            if (gap == 2) {
                gap = 1
                continue
            }
            gap /= 3
        }
    }

    fun <T: Any> shellSort(list: MutableList<T>, comparator: (T, T) -> Int) {
        var gap: Int = list.size / 3 + 1
        while (gap > 0) {
            var i = gap
            while (i < list.size) {
                val current = list[i]
                var prev = i - gap
                while (prev >= 0 && comparator.invoke(list[prev], current) > 0) {
                    list[prev + gap] = list[prev]
                    prev -= gap
                }
                list[prev + gap] = current
                i += gap
            }
            if (gap == 2) {
                gap = 1
                continue
            }
            gap /= 3
        }
    }

    fun <T: Any> mergeSort(arr: Array<T>, comparator: (T, T) -> Int) {
        if (arr.size < 2) {
            return
        }
        val mid: Int = arr.size / 2
        val left = arr.copyOfRange(0, mid)
        mergeSort(left, comparator)
        val right = arr.copyOfRange(mid, arr.size)
        mergeSort(right, comparator)
        merge(left, right, comparator, arr)
    }

    private fun <T: Any> merge(left: Array<T>, right: Array<T>, comparator: (T, T) -> Int, res: Array<T>) {
        var ri = 0
        var pli = 0
        var pri = 0
        while (pli < left.size && pri < right.size) {
            if (comparator.invoke(left[pli], right[pri]) <= 0) {
                res[ri++] = left[pli++]
            }
            else {
                res[ri++] = right[pri++]
            }
        }
        while (pli < left.size) {
            res[ri++] = left[pli++]
        }
        while (pri < right.size) {
            res[ri++] = right[pri++]
        }
    }

    fun <T: Any> mergeSort(list: MutableList<T>, comparator: (T, T) -> Int) {
        if (list.size < 2) {
            return
        }
        val mid: Int = list.size / 2
        val left = list.subList(0, mid)
        mergeSort(left, comparator)
        val right = list.subList(mid, list.size)
        mergeSort(right, comparator)
        merge(left.toMutableList(), right.toMutableList(), comparator, list)
    }

    private fun <T: Any> merge(left: MutableList<T>, right: MutableList<T>, comparator: (T, T) -> Int, res: MutableList<T>) {
        var ri = 0
        var pli = 0
        var pri = 0
        while (pli < left.size && pri < right.size) {
            if (comparator.invoke(left[pli], right[pri]) <= 0) {
                res[ri++] = left[pli++]
            }
            else {
                res[ri++] = right[pri++]
            }
        }
        while (pli < left.size) {
            res[ri++] = left[pli++]
        }
        while (pri < right.size) {
            res[ri++] = right[pri++]
        }
    }

    @JvmOverloads
    fun <T: Any> quickSort(arr: Array<T>, left: Int = 0, right: Int = arr.size - 1, comparator: (T, T) -> Int) {
        if (left >= right) {
            return
        }
        val sentry: T = arr[left]
        var i = left
        var j = right
        while (i < j) {
            while (i < j && comparator.invoke(arr[j], sentry) > 0) {
                --j
            }
            if (i < j) {
                arr[i++] = arr[j]
            }
            while (i < j && comparator.invoke(arr[i], sentry) <= 0) {
                ++i
            }
            if (i < j) {
                arr[j--] = arr[i]
            }
        }
        arr[i] = sentry
        quickSort(arr, left, i - 1, comparator)
        quickSort(arr, i + 1, right, comparator)
    }

    @JvmOverloads
    fun <T: Any> quickSort(list: MutableList<T>, left: Int = 0, right: Int = list.size - 1, comparator: (T, T) -> Int) {
        if (left >= right) {
            return
        }
        val sentry: T = list[left]
        var i = left
        var j = right
        while (i < j) {
            while (i < j && comparator.invoke(list[j], sentry) > 0) {
                --j
            }
            if (i < j) {
                list[i++] = list[j]
            }
            while (i < j && comparator.invoke(list[i], sentry) <= 0) {
                ++i
            }
            if (i < j) {
                list[j--] = list[i]
            }
        }
        list[i] = sentry
        quickSort(list, left, i - 1, comparator)
        quickSort(list, i + 1, right, comparator)
    }

    fun <T: Any> heapSort(arr: Array<T>, comparator: (T, T) -> Int) {
        for (i in arr.size / 2 downTo 1) {
            makeHeap(arr, comparator, i, arr.size)
        }
        for (i in arr.size downTo 1) {
            val tmp = arr[0]
            arr[0] = arr[i - 1]
            arr[i - 1] = tmp
            makeHeap(arr, comparator, 0, i - 1)
        }
    }

    private fun <T: Any> makeHeap(arr: Array<T>, comparator: (T, T) -> Int, top: Int, len: Int) {
        val left = top * 2 + 1
        val right = top * 2 + 2
        var largest = top
        if (left < len && comparator.invoke(arr[left], arr[largest]) > 0) {
            largest = left
        }
        if (right < len && comparator.invoke(arr[right], arr[largest]) > 0) {
            largest = right
        }
        if (largest != top) {
            val tmp: T = arr[top]
            arr[top] = arr[largest]
            arr[largest] = tmp
            makeHeap(arr, comparator, largest, len)
        }
    }

    fun <T: Any> heapSort(list: MutableList<T>, comparator: (T, T) -> Int) {
        for (i in list.size / 2 downTo 1) {
            makeHeap(list, comparator, i, list.size)
        }
        for (i in list.size downTo 1) {
            val tmp = list[0]
            list[0] = list[i - 1]
            list[i - 1] = tmp
            makeHeap(list, comparator, 0, i - 1)
        }
    }

    private fun <T: Any> makeHeap(arr: MutableList<T>, comparator: (T, T) -> Int, top: Int, len: Int) {
        val left = top * 2 + 1
        val right = top * 2 + 2
        var largest = top
        if (left < len && comparator.invoke(arr[left], arr[largest]) > 0) {
            largest = left
        }
        if (right < len && comparator.invoke(arr[right], arr[largest]) > 0) {
            largest = right
        }
        if (largest != top) {
            val tmp: T = arr[top]
            arr[top] = arr[largest]
            arr[largest] = tmp
            makeHeap(arr, comparator, largest, len)
        }
    }

    fun <T: Any> countSort(arr: Array<T>, map: SortedMap<T, Int?>) {
        for (ar in arr) {
            if (map[ar] == null) {
                map[ar] = 0
            }
            map[ar] = map[ar]?.plus(1)
        }
        var index = 0
        for (entry in map) {
            entry.value?.let {
                for (count in 0 until it) {
                    arr[index++] = entry.key
                }
            }
        }
    }

    fun <T: Any> countSort(arr: MutableList<T>, map: SortedMap<T, Int?>) {
        for (ar in arr) {
            if (map[ar] == null) {
                map[ar] = 0
            }
            map[ar] = map[ar]?.plus(1)
        }
        var index = 0
        for (entry in map) {
            entry.value?.let {
                for (count in 0 until it) {
                    arr[index++] = entry.key
                }
            }
        }
    }
}