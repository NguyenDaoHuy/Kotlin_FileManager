package com.example.kotlin_filemanager.audio.audioplayer

import android.app.Activity
import android.widget.SeekBar

interface IAudioPlayerView {
    fun setResourcesWithMusic()
    fun setTextCurrenTime()
    fun setPlaying()
    fun setPausing()
    fun getActivityPlayerAudio() : Activity?
    fun getSeekBarPlayerAudio() : SeekBar?
}