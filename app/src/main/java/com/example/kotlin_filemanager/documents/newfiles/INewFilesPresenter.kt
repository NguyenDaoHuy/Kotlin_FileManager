package com.example.kotlin_filemanager.documents.newfiles

import com.example.kotlin_filemanager.model.Item

interface INewFilesPresenter {
    fun inti()
    fun getNewFileList() : ArrayList<Item>?
}