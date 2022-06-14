package com.example.kotlin_filemanager.image.imagefiles

import android.annotation.SuppressLint
import android.provider.MediaStore
import com.example.kotlin_filemanager.model.FileItem

class ImageFilesPresenter(private var folderName: String?, private var view: IImageFileView?) : IImageFilePresenter {
    private var filesImageList : ArrayList<FileItem>? = ArrayList()
    private var fileItem : FileItem? = null

    @SuppressLint("Range")
    private fun fetchMedia(){
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Images.Media.DATA + " like?"
        val selectionArg = arrayOf("%$folderName%")
        val cursor = view!!.getActivityImageFile()!!.contentResolver.query(uri, null, selection, selectionArg, null)
        if (cursor != null && cursor.moveToNext()) {
            do {
                val id = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                val title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                val displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME))
                val size = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE))
                val duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                val dateAdded = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED))
                fileItem = FileItem(id, title, displayName, size, duration, path, dateAdded)
                filesImageList!!.add(fileItem!!)
            } while (cursor.moveToNext())
        }
    }

    override fun init() {
        fetchMedia()
    }

    override fun getFilesImageList(): ArrayList<FileItem>? {
        return filesImageList!!
    }


}