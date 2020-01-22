package com.example.myjetpackapplication.business.video.view

import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.VideoView
import androidx.databinding.*
import androidx.recyclerview.widget.RecyclerView
import com.example.myjetpackapplication.business.video.view.databinding.ItemVideoViewBinding
import com.example.myjetpackapplication.business.video.view.databinding.ItemVideoViewLandBinding
import com.example.myjetpackapplication.business.video.view.event.VideoListScrollEvent
import com.github.seelikes.android.media.data.MediaInfo
import com.github.seelikes.android.media.video.VideoUtil
import com.github.seelikes.android.mvvm.basic.BasicViewHolder
import kotlinx.android.synthetic.main.item_video_view.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference

/**
 * Created by liutiantian on 2020-01-17 22:25 星期五
 */
class VideoViewViewHolder<B : ViewDataBinding>(weakContext: WeakReference<Context>, binding: B) : BasicViewHolder<MediaInfo, B>(weakContext, binding) {
    val title = ObservableField<String>()
    val thumbnailBitmap = ObservableField<Bitmap>()
    val newState = ObservableInt(RecyclerView.SCROLL_STATE_IDLE)
    val playing = ObservableBoolean()

    var width: Int? = null
    var height: Int? = null
    var job: Job? = null

    init {
        newState.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (job?.isCompleted == true) {
                    return
                }
                if (newState.get() == RecyclerView.SCROLL_STATE_IDLE) {
                    job = GlobalScope.launch {
                        getThumbnail()
                    }
                }
                else {
                    job?.cancel()
                }
            }
        })
    }

    override fun setData(entity: MediaInfo?) {
        super.setData(entity)
        job?.cancel()
        job = null

        title.set(entity?.title)
        val params = thumbnail?.layoutParams
        width = params?.width
        height = params?.height
        newState.notifyChange()
    }

    private suspend fun getThumbnail() {
        coroutineScope {
            launch {
                val finalWidth = width ?: 0
                val finalHeight = height ?: 0
                if (finalWidth > 0 && finalHeight > 0) {
                    entity?.data?.let {
                        thumbnailBitmap.set(VideoUtil.getThumbnail(it, finalWidth, finalHeight, MediaStore.Video.Thumbnails.MINI_KIND))
                    }
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onVideoClick(event: MediaInfo?) {
        event?.let {
            if (it.id == entity?.id) {
                playing.set(true)
                setVideoPath(videoView)
            }
            else {
                playing.set(false)
                stopVideo(videoView)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onListScroll(event: VideoListScrollEvent) {
        newState.set(event.newState ?: RecyclerView.SCROLL_STATE_IDLE)
    }

    fun onViewDetachedFromWindow() {
        if (job?.isCompleted == true) {
            return
        }
        job?.cancel()
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

val <B : ViewDataBinding> VideoViewViewHolder<B>.thumbnail: ImageView?
    get() {
        return when (binding) {
            is ItemVideoViewBinding -> {
                binding.thumbnail
            }
            is ItemVideoViewLandBinding -> {
                binding.thumbnail
            }
            else -> {
                null
            }
        }
    }

val <B : ViewDataBinding> VideoViewViewHolder<B>.videoView: VideoView?
    get() {
        return when (binding) {
            is ItemVideoViewBinding -> {
                binding.videoView
            }
            is ItemVideoViewLandBinding -> {
                binding.videoView
            }
            else -> {
                null
            }
        }
    }