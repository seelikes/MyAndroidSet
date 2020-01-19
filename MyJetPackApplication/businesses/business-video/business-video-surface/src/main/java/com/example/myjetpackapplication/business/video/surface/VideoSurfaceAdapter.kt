package com.example.myjetpackapplication.business.video.surface

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.business.video.surface.databinding.ItemVideoSurfaceBinding
import com.github.seelikes.android.media.MediaInfoDiffUtil
import com.github.seelikes.android.media.data.MediaInfo
import com.github.seelikes.android.mvvm.basic.BasicPagedListAdapter
import java.lang.ref.WeakReference

/**
 * Created by liutiantian on 2020-01-19 13:34 星期日
 */
class VideoSurfaceAdapter(context: Context, itemClickListener: (item: MediaInfo?, position: Int) -> Unit) : BasicPagedListAdapter<MediaInfo, VideoSurfaceViewHolder, MediaInfoDiffUtil, ItemVideoSurfaceBinding>(context, MediaInfoDiffUtil(), itemClickListener) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoSurfaceViewHolder = VideoSurfaceViewHolder(WeakReference(context), ItemVideoSurfaceBinding.inflate(LayoutInflater.from(context)))
}