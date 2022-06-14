package com.example.kotlin_filemanager.audio.audioplayer

import android.media.MediaPlayer
import android.os.Handler
import com.example.kotlin_filemanager.MyMediaPlayer
import com.example.kotlin_filemanager.model.FileItem
import java.io.IOException
import java.util.ArrayList

class AudioPlayerPresenter(view: IAudioPlayerView) : IAudioPlayerPresenter{
     private var view : IAudioPlayerView? = view

    override fun playMusic(mediaPlayer: MediaPlayer?, audio: FileItem) {
        mediaPlayer!!.reset()
        try {
            mediaPlayer.setDataSource(audio.path)
            mediaPlayer.prepare()
            mediaPlayer.start()
            view!!.getSeekBarPlayerAudio()!!.progress = 0
            view!!.getSeekBarPlayerAudio()!!.max = mediaPlayer.duration
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun nextMusic(audioArrayList: ArrayList<FileItem>?, mediaPlayer: MediaPlayer?) {
        if (MyMediaPlayer.currentIndex == audioArrayList!!.size - 1)
            return
        MyMediaPlayer.currentIndex += 1
        mediaPlayer!!.reset()
        view!!.setResourcesWithMusic()
    }

    override fun previousMusic(mediaPlayer: MediaPlayer?) {
        if (MyMediaPlayer.currentIndex == 0)
            return
        MyMediaPlayer.currentIndex -= 1
        mediaPlayer!!.reset()
        view!!.setResourcesWithMusic()
    }

    override fun pauseMusic(mediaPlayer: MediaPlayer?) {
        if (mediaPlayer!!.isPlaying)
              mediaPlayer.pause()
        else
            mediaPlayer.start()
    }

    override fun checkPlaying(mediaPlayer: MediaPlayer?) {
        view!!.getActivityPlayerAudio()!!.runOnUiThread(object : Runnable {
            override fun run() {
                if (mediaPlayer != null) {
                    view!!.getSeekBarPlayerAudio()!!.progress = mediaPlayer.currentPosition
                    view!!.setTextCurrenTime()
                    if (mediaPlayer.isPlaying) {
                        view!!.setPausing()
                    } else {
                        view!!.setPlaying()
                    }
                }
                Handler().postDelayed(this, 100)
            }
        })
    }

}