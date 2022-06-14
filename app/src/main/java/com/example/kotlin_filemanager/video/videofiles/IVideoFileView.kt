package com.example.kotlin_filemanager.video.videofiles

import android.app.Activity

interface IVideoFileView {
    fun getActivityVideoFile() : Activity?
    fun initRecycleview()
}