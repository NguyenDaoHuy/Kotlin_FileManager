package com.example.kotlin_filemanager.audio.audiofiles

import com.example.kotlin_filemanager.model.FileItem

interface IAudioFilePresenter {
    fun init()
    fun getFilesAudioList() : ArrayList<FileItem>?
}