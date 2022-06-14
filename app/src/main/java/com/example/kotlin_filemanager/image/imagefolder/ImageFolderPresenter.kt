package com.example.kotlin_filemanager.image.imagefolder

import android.annotation.SuppressLint
import android.provider.MediaStore

class ImageFolderPresenter(view: IImageFolderView) : IImageFolderPresenter {
    private var folderImageList : ArrayList<String>? = ArrayList()
    private var view : IImageFolderView? = view

    @SuppressLint("Range")
    private fun getFolderImage() {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        @SuppressLint("Recycle") val cursor = view!!.getActivityImageFolder()!!.contentResolver.query(uri, null, null, null, null)
        if (cursor != null && cursor.moveToNext()) {
            do {
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
                val index = path.lastIndexOf("/")
                val subString = path.substring(0, index)
                if (!folderImageList!!.contains(subString)) {
                    folderImageList!!.add(subString)
                }
            } while (cursor.moveToNext())
        }
    }

    override fun getFolderImageList() : ArrayList<String>?{
        return folderImageList
    }

    override fun inti() {
        getFolderImage()
    }
}