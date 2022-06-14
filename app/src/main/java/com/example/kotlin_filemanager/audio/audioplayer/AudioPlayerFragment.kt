package com.example.kotlin_filemanager.audio.audioplayer

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.kotlin_filemanager.MyMediaPlayer
import com.example.kotlin_filemanager.R
import com.example.kotlin_filemanager.databinding.FragmentAudioPlayerBinding
import com.example.kotlin_filemanager.model.FileItem
import java.util.concurrent.TimeUnit

class AudioPlayerFragment : Fragment(R.layout.fragment_audio_player),IAudioPlayerView {
    private var _binding : FragmentAudioPlayerBinding? = null
    private val binding get() = _binding!!
    private var iAudioPlayerPresenter : IAudioPlayerPresenter? = null
    private var mediaPlayer = MyMediaPlayer.getInstance()
    private var audioArrayList : ArrayList<FileItem>? = null
    private var position = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAudioPlayerBinding.inflate(inflater,container,false)
        iAudioPlayerPresenter = AudioPlayerPresenter(this)
        position = arguments?.getInt("position")!!
        audioArrayList = arguments?.getParcelableArrayList("LIST")
        binding.audioName.isSelected = true
        onClick()
        setResourcesWithMusic()
        iAudioPlayerPresenter!!.checkPlaying(mediaPlayer)
        return binding.root
    }

    private fun onClick(){
        binding.btnBack.setOnClickListener {
            if (requireActivity().supportFragmentManager != null) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer!!.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })
        binding.next.setOnClickListener {
            iAudioPlayerPresenter!!.nextMusic(audioArrayList, mediaPlayer)
        }
        binding.previous.setOnClickListener {
            iAudioPlayerPresenter!!.previousMusic(mediaPlayer)
        }
        binding.pausePlay.setOnClickListener {
            iAudioPlayerPresenter!!.pauseMusic(mediaPlayer)
        }
    }

    override fun setResourcesWithMusic() {
        val audio = audioArrayList!![position]
        binding.audioName.text = audio.displayName
        binding.totalTime.text = convertToMMSS(audio.duration.toString())
        iAudioPlayerPresenter!!.playMusic(mediaPlayer, audio)
    }

    override fun setTextCurrenTime() {
        binding.currentTime.text = convertToMMSS(mediaPlayer!!.currentPosition.toString() + "")
    }

    override fun setPlaying() {
        binding.pausePlay.setImageResource(R.drawable.ic_play)
    }

    override fun setPausing() {
        binding.pausePlay.setImageResource(R.drawable.ic_pause)
    }

    override fun getActivityPlayerAudio() : Activity? {
        return activity
    }

    override fun getSeekBarPlayerAudio(): SeekBar {
        return binding.seekBar
    }

    companion object {
        val TAG: String = AudioPlayerFragment::class.java.name
    }
    private fun convertToMMSS(duration: String): String {
        val millis = duration.toLong()
        return String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
        )
    }
}