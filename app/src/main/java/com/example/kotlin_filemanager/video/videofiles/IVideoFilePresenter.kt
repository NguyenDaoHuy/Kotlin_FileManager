package com.example.kotlin_filemanager.video.videofiles

import com.example.kotlin_filemanager.model.FileItem

interface IVideoFilePresenter {
    fun init()
    fun getFilesVideoList(): ArrayList<FileItem>?
}