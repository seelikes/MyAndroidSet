package com.example.myjetpackapplication.business.video.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.myjetpackapplication.business.video.view.databinding.ItemVideoViewBinding
import com.github.seelikes.android.media.data.MediaInfo
import com.github.seelikes.android.mvvm.basic.BasicPagedListAdapter
import java.lang.ref.WeakReference

class VideoDiffUtil : DiffUtil.ItemCallback<MediaInfo>() {
    override fun areItemsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean = oldItem == newItem
}

/**
 * Created by liutiantian on 2020-01-17 22:21 星期五
 */
class VideoViewAdapter(context: Context, itemClickListener: (item: MediaInfo?, position: Int) -> Unit) : BasicPagedListAdapter<MediaInfo, VideoViewViewHolder, VideoDiffUtil, ItemVideoViewBinding>(context, VideoDiffUtil(), itemClickListener) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewViewHolder {
        return VideoViewViewHolder(WeakReference(context), ItemVideoViewBinding.inflate(LayoutInflater.from(context)))
    }

    override fun onViewDetachedFromWindow(holder: VideoViewViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onViewDetachedFromWindow()
    }
}