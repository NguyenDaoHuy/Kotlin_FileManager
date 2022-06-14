package com.example.kotlin_filemanager.documents.documentfiles

import com.example.kotlin_filemanager.model.Item
import java.util.ArrayList

interface IDocumentFilePresenter {
    fun inti()
    fun getDocumenFileList() : ArrayList<Item?>?
}