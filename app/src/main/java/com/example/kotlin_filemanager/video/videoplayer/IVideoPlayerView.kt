package com.example.kotlin_filemanager.video.videoplayer

import android.content.Context
import android.media.MediaPlayer
import com.google.android.exoplayer2.SimpleExoPlayer

interface IVideoPlayerView {
    fun getMediaPlayer() : MediaPlayer?
    fun getContextPlayerVideo() : Context?
    fun playerView(player: SimpleExoPlayer?)
    fun playErrorToast(str: String?)
}