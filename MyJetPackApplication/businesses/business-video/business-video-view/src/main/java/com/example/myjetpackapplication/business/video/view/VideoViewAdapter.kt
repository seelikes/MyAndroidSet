package com.example.myjetpackapplication.business.video.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myjetpackapplication.business.video.view.databinding.ItemVideoViewBinding
import com.example.myjetpackapplication.business.video.view.databinding.ItemVideoViewLandBinding
import com.github.seelikes.android.media.MediaInfoDiffUtil
import com.github.seelikes.android.media.data.MediaInfo
import com.github.seelikes.android.mvvm.basic.BasicPagedListAdapter
import java.lang.ref.WeakReference

private const val ITEM_TYPE_VERTICAL = 0
private const val ITEM_TYPE_HORIZONTAL = 1

/**
 * Created by liutiantian on 2020-01-17 22:21 星期五
 */
class VideoViewAdapter(context: Context, itemClickListener: (item: MediaInfo?, position: Int) -> Unit) : BasicPagedListAdapter<MediaInfo, VideoViewViewHolder<*>, MediaInfoDiffUtil>(context, MediaInfoDiffUtil(), itemClickListener) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewViewHolder<*> {
        if (viewType == ITEM_TYPE_VERTICAL) {
            return VideoViewViewHolder(WeakReference(context), ItemVideoViewBinding.inflate(LayoutInflater.from(context)))
        }
        return VideoViewViewHolder(WeakReference(context), ItemVideoViewLandBinding.inflate(LayoutInflater.from(context)))
    }

    override fun getItemViewType(position: Int): Int {
        val mediaInfo = getItem(position)
        mediaInfo?.let {
            if (it.width ?: 0 > it.height ?: 0) {
                return ITEM_TYPE_HORIZONTAL
            }
            else {
                return ITEM_TYPE_VERTICAL
            }
        }
        return ITEM_TYPE_VERTICAL
    }

    override fun onViewDetachedFromWindow(holder: VideoViewViewHolder<*>) {
        super.onViewDetachedFromWindow(holder)
        holder.onViewDetachedFromWindow()
    }
}