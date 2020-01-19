package com.example.myjetpackapplication.business.video.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.business.video.view.databinding.ItemVideoViewBinding
import com.github.seelikes.android.media.MediaInfoDiffUtil
import com.github.seelikes.android.media.data.MediaInfo
import com.github.seelikes.android.mvvm.basic.BasicPagedListAdapter
import java.lang.ref.WeakReference

/**
 * Created by liutiantian on 2020-01-17 22:21 星期五
 */
class VideoViewAdapter(context: Context, itemClickListener: (item: MediaInfo?, position: Int) -> Unit) : BasicPagedListAdapter<MediaInfo, VideoViewViewHolder, MediaInfoDiffUtil, ItemVideoViewBinding>(context, MediaInfoDiffUtil(), itemClickListener) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewViewHolder {
        return VideoViewViewHolder(WeakReference(context), ItemVideoViewBinding.inflate(LayoutInflater.from(context)))
    }

    override fun onViewDetachedFromWindow(holder: VideoViewViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onViewDetachedFromWindow()
    }
}