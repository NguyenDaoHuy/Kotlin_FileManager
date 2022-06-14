package com.example.kotlin_filemanager.documents.documentfiles

import android.os.Environment
import com.example.kotlin_filemanager.model.Item
import java.io.File
import java.lang.Exception
import java.util.ArrayList

class DocumentsFilePresenterI : IDocumentFilePresenter{

    private val itemDocumentsArrayList: ArrayList<Item?> = ArrayList()

    override fun inti() {
        val dir = File(Environment.getExternalStorageDirectory().toString())
        walkdir(dir)
    }
    private fun walkdir(dir: File) {
        val listFile = dir.listFiles()
        try {
            if (listFile.isNotEmpty()) {
                for (file in listFile) {
                    if (file.isDirectory) {
                        walkdir(file)
                    } else {
                        val name = file.name
                        val path = file.path
                        if (file.name.endsWith(".docx") ||
                            file.name.endsWith(".pdf") ||
                            file.name.endsWith(".txt") ||
                            file.name.endsWith(".pptx") ||
                            file.name.endsWith(".xls")
                        ) {
                            itemDocumentsArrayList.add(Item(name, path))
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun getDocumenFileList() : ArrayList<Item?> {
        return itemDocumentsArrayList
    }
}