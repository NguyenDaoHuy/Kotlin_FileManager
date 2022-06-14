package com.example.kotlin_filemanager.audio.audiofiles

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_filemanager.R
import com.example.kotlin_filemanager.audio.audioplayer.AudioPlayerFragment
import com.example.kotlin_filemanager.databinding.FragmentAudioFileBinding
import com.example.kotlin_filemanager.model.FileItem

class AudioFileFragment : Fragment(R.layout.fragment_audio_file),IAudioFileView,AudioFileListAdapter.AudioFileInterface {
    private var _binding : FragmentAudioFileBinding? = null
    private val binding get() = _binding!!
    private var iAudioFilePresenter : IAudioFilePresenter? = null
    private var audioAdapter : AudioFileListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAudioFileBinding.inflate(inflater,container,false)
        val folder_name = arguments?.getString("nameOFFolder")
        iAudioFilePresenter = AudioFilePresenter(folder_name,this)
        iAudioFilePresenter!!.init()
        onClick()
        binding.tvAudioFolder.text = folder_name
        initRecycleview()
        return binding.root
    }

    private fun onClick(){
        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
    companion object {
        val TAG: String = AudioFileFragment::class.java.name
    }

    override fun getActivityAudioFile(): Activity? {
        return  activity
    }

    override fun initRecycleview() {
        audioAdapter = AudioFileListAdapter(this)
        binding.audioRv.adapter = audioAdapter
        binding.audioRv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    override val count: Int
        get() = iAudioFilePresenter!!.getFilesAudioList()!!.size

    override fun audio(position: Int): FileItem {
        return iAudioFilePresenter!!.getFilesAudioList()!![position]
    }

    override fun onClickItem(position: Int) {
        val audioPlayerFragment = AudioPlayerFragment()
        val bundle = Bundle()
        bundle.putInt("position", position)
        bundle.putParcelableArrayList("LIST",iAudioFilePresenter!!.getFilesAudioList()!!)
        audioPlayerFragment.arguments = bundle
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentMain, audioPlayerFragment)
        fragmentTransaction.addToBackStack(AudioPlayerFragment.TAG)
        fragmentTransaction.commit()
    }

    override fun context(): Context? {
        return  context
    }
}