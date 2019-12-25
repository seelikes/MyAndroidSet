package com.example.myjetpackapplication.business.paging

import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myjetpackapplication.business.paging.data.PagingDataSource
import com.example.myjetpackapplication.business.paging.data.PagingEntity

/**
 * Created by liutiantian on 2019-05-14 13:49 星期二
 */
class PagingDataModel : ViewModel() {
    val list by lazy {
        LivePagedListBuilder(
            object : DataSource.Factory<Long, PagingEntity>() {
                override fun create(): DataSource<Long, PagingEntity> {
                    return PagingDataSource()
                }
            },
            PagedList.Config.Builder()
                .setPrefetchDistance(30)
                .setPageSize(15)
                .setInitialLoadSizeHint(30)
                .setEnablePlaceholders(false)
                .build()
        ).build()
    }
}