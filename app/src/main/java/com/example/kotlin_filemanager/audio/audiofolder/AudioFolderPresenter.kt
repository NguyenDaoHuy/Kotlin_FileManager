package com.example.kotlin_filemanager.audio.audiofolder

import android.annotation.SuppressLint
import android.provider.MediaStore
import kotlin.collections.ArrayList

class AudioFolderPresenter(view: IAudioFolderView) : IAudioFolderPresenter{
    private var folderAudioList : ArrayList<String>? = ArrayList()
    private var view : IAudioFolderView? = view

    @SuppressLint("Range")
    private fun getFolderAudio() {
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        @SuppressLint("Recycle") val cursor = view!!.getActivityAudioFolder()!!.contentResolver.query(uri, null, null, null, null)
        if (cursor != null && cursor.moveToNext()) {
            do {
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                val index = path.lastIndexOf("/")
                val subString = path.substring(0, index)
                if (!folderAudioList!!.contains(subString)) {
                    folderAudioList!!.add(subString)
                }
            } while (cursor.moveToNext())
        }
    }
    fun getFolderAudioList() : ArrayList<String>?{
        return folderAudioList
    }

    override fun inti() {
        getFolderAudio()
    }

    override fun getFolderImageList(): ArrayList<String>? {
        return folderAudioList
    }
}