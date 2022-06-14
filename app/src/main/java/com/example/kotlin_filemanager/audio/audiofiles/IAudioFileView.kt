package com.example.kotlin_filemanager.audio.audiofiles

import android.app.Activity

interface IAudioFileView {
    fun getActivityAudioFile() : Activity?
    fun initRecycleview()
}