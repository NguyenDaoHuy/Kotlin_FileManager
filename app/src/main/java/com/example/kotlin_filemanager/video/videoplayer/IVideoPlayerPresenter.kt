package com.example.kotlin_filemanager.video.videoplayer

import com.example.kotlin_filemanager.model.FileItem
import com.google.android.exoplayer2.SimpleExoPlayer
import java.util.ArrayList

interface IVideoPlayerPresenter {
    fun playerVideo(fileItemArrayList: ArrayList<FileItem>?, position: Int?,player: SimpleExoPlayer?)
    fun playError(player: SimpleExoPlayer?)
}