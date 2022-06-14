package com.example.kotlin_filemanager.documents.newfiles

import android.os.Environment
import com.example.kotlin_filemanager.model.Item
import java.io.File
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class NewFilesPresenter :INewFilesPresenter{
    private val itemNewFileArrayList : ArrayList<Item> = ArrayList()
    override fun inti() {
        val dir = File(Environment.getExternalStorageDirectory().toString())
        walkdir(dir)
    }
    private fun walkdir(dir: File) {
        val listFile = dir.listFiles()
        val monthNow = Calendar.getInstance()[Calendar.MONTH] + 1
        val yearNow = Calendar.getInstance()[Calendar.YEAR]
        try {
            if (listFile.isNotEmpty()) {
                for (file in listFile) {
                    if (file.isDirectory) {
                        walkdir(file)
                    } else {
                        val name = file.name
                        val path = file.path
                        val date = Date(file.lastModified())
                        val cal = Calendar.getInstance()
                        cal.time = date
                        val month = cal[Calendar.MONTH] + 1
                        val year = cal[Calendar.YEAR]
                        if (file.name.endsWith(".docx") ||
                            file.name.endsWith(".pdf") ||
                            file.name.endsWith(".txt") ||
                            file.name.endsWith(".pptx") ||
                            file.name.endsWith(".xls")
                        ) {
                            if (month == monthNow && year == yearNow) {
                                itemNewFileArrayList.add(Item(name, path))
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun getNewFileList() : ArrayList<Item> {
        return itemNewFileArrayList
    }
}