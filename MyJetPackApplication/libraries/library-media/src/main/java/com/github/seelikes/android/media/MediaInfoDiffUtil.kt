package com.github.seelikes.android.media

import androidx.recyclerview.widget.DiffUtil
import com.github.seelikes.android.media.data.MediaInfo

/**
 * Created by liutiantian on 2020-01-19 15:05 星期日
 */
class MediaInfoDiffUtil : DiffUtil.ItemCallback<MediaInfo>() {
    override fun areItemsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean = oldItem == newItem
}