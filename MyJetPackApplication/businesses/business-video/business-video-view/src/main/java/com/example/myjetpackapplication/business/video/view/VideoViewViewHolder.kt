package com.example.myjetpackapplication.business.video.view

import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.VideoView
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.example.myjetpackapplication.business.video.view.databinding.ItemVideoViewBinding
import com.github.seelikes.android.media.data.MediaInfo
import com.github.seelikes.android.media.video.VideoUtil
import com.github.seelikes.android.mvvm.basic.BasicViewHolder
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by liutiantian on 2020-01-17 22:25 星期五
 */
class VideoViewViewHolder(context: Context, binding: ItemVideoViewBinding) : BasicViewHolder<MediaInfo, ItemVideoViewBinding>(context, binding) {
    val title = ObservableField<String>()
    val thumbnail = ObservableField<Bitmap>()
    val thumbnailLand = ObservableField<Bitmap>()
    val land = ObservableBoolean()
    val playing = ObservableBoolean()

    override fun setData(entity: MediaInfo?) {
        super.setData(entity)
        title.set(entity?.title)
        land.set(entity?.width ?: 0 > entity?.height ?: 0)
        if (land.get()) {
            val params = binding.thumbnailLand.layoutParams
            entity?.data?.let {
                thumbnail.set(null)
                thumbnailLand.set(VideoUtil.getThumbnail(it, params.width, params.height, MediaStore.Video.Thumbnails.MINI_KIND))
            }
        }
        else {
            val params = binding.thumbnail.layoutParams
            entity?.data?.let {
                thumbnail.set(VideoUtil.getThumbnail(it, params.width, params.height, MediaStore.Video.Thumbnails.MINI_KIND))
                thumbnailLand.set(null)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onVideoClick(event: MediaInfo?) {
        event?.let {
            if (it.id == entity?.id) {
                playing.set(true)
                if (land.get()) {
                    stopVideo(binding.videoView)
                    setVideoPath(binding.videoViewLand)
                }
                else {
                    setVideoPath(binding.videoView)
                    stopVideo(binding.videoViewLand)
                }
            }
            else {
                playing.set(false)
                stopVideo(binding.videoView)
                stopVideo(binding.videoViewLand)
            }
        }
    }

    fun onRecycled() {
        thumbnail.get()?.recycle()
        thumbnail.set(null)
        thumbnailLand.get()?.recycle()
        thumbnailLand.set(null)
    }

    private fun setVideoPath(videoView: VideoView?) {
        videoView?.setVideoPath(entity?.data)
        if (videoView?.isPlaying != true) {
            videoView?.setOnCompletionListener {
                it.start()
            }
            videoView?.start()
        }
    }

    private fun stopVideo(videoView: VideoView?) {
        if (videoView?.isPlaying == true) {
            videoView.setOnCompletionListener {  }
            videoView.stopPlayback()
        }
    }
}