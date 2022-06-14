package com.example.kotlin_filemanager.video.videofolder

import android.app.Activity

interface IVideoFolderView {
    fun notData(str : String?)
    fun getActivityImageFolder() : Activity?
    fun initRecycleview()
}