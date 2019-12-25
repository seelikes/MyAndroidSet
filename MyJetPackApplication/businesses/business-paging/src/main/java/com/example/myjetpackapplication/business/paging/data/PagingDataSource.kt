package com.example.myjetpackapplication.business.paging.data

import androidx.paging.PageKeyedDataSource
import com.java.lib.oil.lang.StringManager
import java.nio.charset.Charset
import java.security.SecureRandom
import java.util.*
import kotlin.math.min

/**
 * Created by liutiantian on 2019-05-14 13:08 星期二
 */
class PagingDataSource : PageKeyedDataSource<Long, PagingEntity>() {
    companion object {
        const val MAX_LOAD_SIZE = 1024L
    }

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, PagingEntity>) {
        callback.onResult(load(0, params.requestedLoadSize), null, 2)
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, PagingEntity>) {
        val list = load(params.key, params.requestedLoadSize)
        if (list.isNotEmpty()) {
            callback.onResult(list, params.key + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, PagingEntity>) {
        val list = load(params.key - 1, params.requestedLoadSize)
        if (list.isNotEmpty()) {
            callback.onResult(list, params.key - 1)
        }
    }

    private fun load(start: Long, size: Int): List<PagingEntity> {
        val list = mutableListOf<PagingEntity>()
        val bytes = ByteArray(3)
        for (i in start * 15 until min(start * 15 + size, MAX_LOAD_SIZE)) {
            SecureRandom(SecureRandom().generateSeed(3)).nextBytes(bytes)
            list.add(PagingEntity(i, StringManager.getInstance().hex(bytes).toUpperCase(Locale.US), System.currentTimeMillis()))
        }
        return list
    }
}