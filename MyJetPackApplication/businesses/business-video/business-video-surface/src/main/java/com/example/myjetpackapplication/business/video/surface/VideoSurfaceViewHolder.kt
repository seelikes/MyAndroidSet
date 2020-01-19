package com.example.myjetpackapplication.business.video.surface

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.Uri
import android.provider.MediaStore
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.recyclerview.widget.RecyclerView
import com.example.myjetpackapplication.business.video.surface.databinding.ItemVideoSurfaceBinding
import com.example.myjetpackapplication.business.video.surface.event.VideoListScrollEvent
import com.facebook.imageutils.BitmapUtil
import com.github.seelikes.android.media.data.MediaInfo
import com.github.seelikes.android.media.image.ImageUtil
import com.github.seelikes.android.media.video.VideoUtil
import com.github.seelikes.android.mvvm.basic.BasicViewHolder
import com.github.seelikes.android.mvvm.basic.context
import com.java.lib.oil.file.FileUtils
import com.java.lib.oil.lang.StringManager
import com.orhanobut.logger.Logger
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference

/**
 * Created by liutiantian on 2020-01-19 13:30 星期日
 */
class VideoSurfaceViewHolder(weakContext: WeakReference<Context>, binding: ItemVideoSurfaceBinding) : BasicViewHolder<MediaInfo, ItemVideoSurfaceBinding>(weakContext, binding) {
    val title = ObservableField<String>()
    val bitmapUri = ObservableField<Uri>()
    val land = ObservableBoolean()
    val playing = ObservableBoolean()

    val newState = ObservableInt(RecyclerView.SCROLL_STATE_IDLE)

    var width: Int? = null
    var height: Int? = null
    var job: Job? = null

    var mediaPlayer: MediaPlayer? = null
    var holderLand: SurfaceHolder? = null
    var holder: SurfaceHolder? = null
    var clickWaiting: Boolean? = null

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
        clickWaiting = false
        job?.cancel()
        job = null

        title.set(entity?.title)
        land.set(entity?.width ?: 0 > entity?.height ?: 0)
        if (land.get()) {
            val params = binding.thumbnailLand.layoutParams
            width = params.width
            height = params.height
            binding.surfaceLand.holder.removeCallback(surfaceLandCallback)
            binding.surfaceLand.holder.addCallback(surfaceLandCallback)
            newState.notifyChange()
        }
        else {
            val params = binding.thumbnail.layoutParams
            width = params.width
            height = params.height
            binding.surface.holder.removeCallback(surfaceCallback)
            binding.surface.holder.addCallback(surfaceCallback)
            newState.notifyChange()
        }
    }

    private val surfaceCallback = object : SurfaceHolder.Callback {
        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

        }

        override fun surfaceDestroyed(holder: SurfaceHolder?) {
            this@VideoSurfaceViewHolder.holder = null
            mediaPlayer?.stop()
        }

        override fun surfaceCreated(holder: SurfaceHolder?) {
            this@VideoSurfaceViewHolder.holder = holder
            if (clickWaiting == true && !land.get()) {
                mediaPlayer?.setDisplay(holder)
            }
        }
    }

    private val surfaceLandCallback = object : SurfaceHolder.Callback {
        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

        }

        override fun surfaceDestroyed(holder: SurfaceHolder?) {
            this@VideoSurfaceViewHolder.holderLand = null
            mediaPlayer?.stop()
        }

        override fun surfaceCreated(holder: SurfaceHolder?) {
            this@VideoSurfaceViewHolder.holderLand = holder
            if (clickWaiting == true && land.get()) {
                mediaPlayer?.setDisplay(holder)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onVideoClick(event: MediaInfo?) {
        event?.let {
            if (it.id == entity?.id) {
                Logger.i("X112211X, playing: ${playing.get()}")
                if (playing.get()) {
                    return
                }
                Logger.i("X112211X, before set dataSource")
                recreateMediaPlayer()
                mediaPlayer?.setDataSource(entity?.data)
                Logger.i("X112211X, after set dataSource")
                if (land.get()) {
                    if (holderLand != null) {
                        mediaPlayer?.setDisplay(holderLand)
                    } else {
                        clickWaiting = true
                    }
                }
                else {
                    if (holder != null) {
                        mediaPlayer?.setDisplay(holder)
                    } else {
                        clickWaiting = true
                    }
                }
                Logger.i("X112211X, reset playing flag")
                playing.set(true)
                mediaPlayer?.prepareAsync()
                Logger.i("X112211X, prepareAsync called")
            }
            else {
                clickWaiting = false
                playing.set(false)
                mediaPlayer?.stop()
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onListScroll(event: VideoListScrollEvent) {
        newState.set(event.newState ?: RecyclerView.SCROLL_STATE_IDLE)
    }

    private fun recreateMediaPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setOnErrorListener { _, what, extra ->
            Toast.makeText(context, "mediaPlayer what: $what; extra: $extra", Toast.LENGTH_LONG).show()
            true
        }
        mediaPlayer?.setOnCompletionListener {
            mediaPlayer?.start()
        }
        mediaPlayer?.setOnPreparedListener {
            mediaPlayer?.start()
        }
    }

    private suspend fun getThumbnail() {
        coroutineScope {
            launch {
                val finalWidth = width ?: 0
                val finalHeight = height ?: 0
                if (finalWidth > 0 && finalHeight > 0) {
                    entity?.data?.let {
                        val cacheFileName = StringManager.getInstance().hex(StringManager.getInstance().md5("$it${if (land.get()) "@land" else ""}".toByteArray(), null))
                        val file = FileUtils.getInstance().locateFile(context?.externalCacheDir, "business/video/surface/thumbnail", "$cacheFileName.webp")
                        if (file.exists()) {
                            bitmapUri.set(Uri.fromFile(file))
                            return@let
                        }
                        ImageUtil.writeBitmapToFile(VideoUtil.getThumbnail(it, finalWidth, finalHeight, MediaStore.Video.Thumbnails.MINI_KIND), file)
                        bitmapUri.set(Uri.fromFile(file))
                    }
                }
            }
        }
    }
}