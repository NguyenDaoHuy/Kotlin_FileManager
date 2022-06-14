package com.example.kotlin_filemanager.audio.audiofiles

import android.annotation.SuppressLint
import android.provider.MediaStore
import com.example.kotlin_filemanager.model.FileItem


class AudioFilePresenter(private var folderName: String?, private var view: IAudioFileView?) : IAudioFilePresenter
{
    private var filesAudioList : ArrayList<FileItem>? = ArrayList()
    private var fileItem : FileItem? = null

    @SuppressLint("Range", "Recycle")
    private fun fetchMedia(){
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.DATA + " like?"
        val selectionArg = arrayOf("%$folderName%")
        val cursor = view!!.getActivityAudioFile()!!.contentResolver.query(
            uri, null,
            selection, selectionArg, null
        )
        if (cursor != null && cursor.moveToNext()) {
            do {
                val id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                val displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                val size = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))
                val duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                val dateAdded = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED))
                fileItem = FileItem(id, title, displayName, size, duration, path, dateAdded)
                filesAudioList!!.add(fileItem!!)
            } while (cursor.moveToNext())
        }
    }

    override fun init() {
        fetchMedia()
    }

    override fun getFilesAudioList(): ArrayList<FileItem>? {
        return filesAudioList
    }

}