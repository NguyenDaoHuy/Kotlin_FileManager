package com.example.kotlin_filemanager.image.imagefiles

import com.example.kotlin_filemanager.model.FileItem

interface IImageFilePresenter {
    fun init()
    fun getFilesImageList() : ArrayList<FileItem>?
}