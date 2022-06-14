package com.example.kotlin_filemanager.video.videoplayer

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kotlin_filemanager.MyMediaPlayer
import com.example.kotlin_filemanager.R
import com.example.kotlin_filemanager.databinding.FragmentVideoPlayerBinding
import com.example.kotlin_filemanager.model.FileItem
import com.google.android.exoplayer2.SimpleExoPlayer

class VideoPlayerFragment : Fragment(R.layout.fragment_video_player),IVideoPlayerView {
    private var _binding : FragmentVideoPlayerBinding? = null
    private val binding get() = _binding!!
    private var iVideoPlayerPresenter : IVideoPlayerPresenter? = null
    private val player: SimpleExoPlayer? = null
    private val mediaPlayer = MyMediaPlayer.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVideoPlayerBinding.inflate(inflater,container,false)
        val position = arguments?.getInt("position")
        val fileVideoArrayList = arguments?.getParcelableArrayList<FileItem>("videoArrayList")
        onClick()
        iVideoPlayerPresenter = VideoPlayerPresenter(this)
        iVideoPlayerPresenter!!.playerVideo(fileVideoArrayList,position,player)
        return binding.root
    }
    private fun onClick(){
        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
    companion object {
        val TAG: String = VideoPlayerFragment::class.java.name
    }

    override fun getMediaPlayer(): MediaPlayer? {
        return mediaPlayer
    }

    override fun playerView(player: SimpleExoPlayer?) {
        binding.exoplayerView.player = player
        binding.exoplayerView.keepScreenOn = true
    }

    override fun playErrorToast(str: String?) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }

    override fun getContextPlayerVideo() : Context? {
        return context
    }
}