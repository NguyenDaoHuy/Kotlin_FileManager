package com.example.kotlin_filemanager.audio.audioplayer

import android.media.MediaPlayer
import com.example.kotlin_filemanager.model.FileItem
import java.util.ArrayList

interface IAudioPlayerPresenter {
    fun playMusic(mediaPlayer: MediaPlayer?, audio: FileItem)
    fun nextMusic(audioArrayList: ArrayList<FileItem>?, mediaPlayer: MediaPlayer?)
    fun previousMusic(mediaPlayer: MediaPlayer?)
    fun pauseMusic(mediaPlayer: MediaPlayer?)
    fun checkPlaying( mediaPlayer: MediaPlayer?)
}