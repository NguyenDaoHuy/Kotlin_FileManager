package com.example.kotlin_filemanager.video.videoplayer

import android.net.Uri
import com.example.kotlin_filemanager.model.FileItem
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import java.io.File
import java.util.ArrayList

class VideoPlayerPresenter(view: IVideoPlayerView) : IVideoPlayerPresenter {
    private var view : IVideoPlayerView? = view

    override fun playerVideo(fileItemArrayList: ArrayList<FileItem>?, position: Int?,
                             player: SimpleExoPlayer?) {
        val player: SimpleExoPlayer?
        view!!.getMediaPlayer()!!.reset()
        val path = fileItemArrayList!![position!!].path
        val uri = Uri.parse(path)
        player = SimpleExoPlayer.Builder(view!!.getContextPlayerVideo()!!).build()
        val defaultDataSourceFactory = DefaultDataSourceFactory(
            view!!.getContextPlayerVideo()!!, Util.getUserAgent(view!!.getContextPlayerVideo()!!, "app")
        )
        val concatenatingMediaSource = ConcatenatingMediaSource()
        for (i in fileItemArrayList.indices) {
            File(fileItemArrayList[i].toString())
            val mediaSource: MediaSource = ProgressiveMediaSource.Factory(defaultDataSourceFactory)
                .createMediaSource(Uri.parse(uri.toString()))
            concatenatingMediaSource.addMediaSource(mediaSource)
        }
        view!!.playerView(player)
        player.prepare(concatenatingMediaSource)
        player.seekTo(position, C.TIME_UNSET)
        playError(player)
    }

    override fun playError(player: SimpleExoPlayer?) {
        player!!.addListener(object : Player.EventListener {
            override fun onPlayerError(error: ExoPlaybackException) {
                view!!.playErrorToast("Video Playing Error")
            }
        })
        player.playWhenReady = true
    }
}