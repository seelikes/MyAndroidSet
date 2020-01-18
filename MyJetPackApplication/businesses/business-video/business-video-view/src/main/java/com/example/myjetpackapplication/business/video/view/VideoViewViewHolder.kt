package com.example.myjetpackapplication.business.video.view

import android.content.Context
import androidx.databinding.ObservableField
import com.example.myjetpackapplication.business.video.view.databinding.ItemVideoViewBinding
import com.github.seelikes.android.media.data.MediaInfo
import com.github.seelikes.android.mvvm.basic.BasicViewHolder

/**
 * Created by liutiantian on 2020-01-17 22:25 星期五
 */
class VideoViewViewHolder(context: Context, binding: ItemVideoViewBinding) : BasicViewHolder<MediaInfo, ItemVideoViewBinding>(context, binding) {
    val title = ObservableField<String>()

    override fun setData(entity: MediaInfo?) {
        super.setData(entity)
        title.set(entity?.title)
    }
}