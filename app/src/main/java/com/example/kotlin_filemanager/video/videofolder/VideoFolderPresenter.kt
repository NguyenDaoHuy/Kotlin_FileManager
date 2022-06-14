package com.example.kotlin_filemanager.video.videofolder

import android.annotation.SuppressLint
import android.provider.MediaStore

class VideoFolderPresenter(view: IVideoFolderView) : IVideoFolderPresenter{
    private var folderVideoList : ArrayList<String>? = ArrayList()
    private var view : IVideoFolderView? = view

    @SuppressLint("Range")
    private fun getFolderVideo() {
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        @SuppressLint("Recycle") val cursor = view!!.getActivityImageFolder()!!.contentResolver.query(uri, null, null, null, null)
        if (cursor != null && cursor.moveToNext()) {
            do {
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                val index = path.lastIndexOf("/")
                val subString = path.substring(0, index)
                if (!folderVideoList!!.contains(subString)) {
                    folderVideoList!!.add(subString)
                }
            } while (cursor.moveToNext())
        }
    }
    override fun inti() {
        getFolderVideo()
    }

    override fun getFolderAudioList(): ArrayList<String>? {
        return folderVideoList
    }
}