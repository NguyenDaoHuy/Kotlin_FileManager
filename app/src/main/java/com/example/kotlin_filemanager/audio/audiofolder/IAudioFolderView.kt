package com.example.kotlin_filemanager.audio.audiofolder

import android.app.Activity

interface IAudioFolderView {
    fun getActivityAudioFolder() : Activity?
    fun initRecycleview()
}