package com.example.myjetpackapplication.business.video.view

import android.app.Application
import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.github.seelikes.android.media.data.MediaInfo
import com.github.seelikes.android.media.data.MediaInfoDataSource

class VideoViewDataModel(application: Application) : AndroidViewModel(application) {
    val videos by lazy {
        LivePagedListBuilder(
            object : DataSource.Factory<Long, MediaInfo>() {
                override fun create(): DataSource<Long, MediaInfo> {
                    return MediaInfoDataSource(application, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, 15)
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