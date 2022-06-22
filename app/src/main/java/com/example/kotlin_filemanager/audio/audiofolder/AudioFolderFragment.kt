package com.example.kotlin_filemanager.audio.audiofolder

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_filemanager.MainActivity
import com.example.kotlin_filemanager.R
import com.example.kotlin_filemanager.adapter.FolderRecyclerViewAdapter
import com.example.kotlin_filemanager.audio.audiofiles.AudioFileFragment
import com.example.kotlin_filemanager.databinding.FragmentAudioFolderBinding
import com.example.kotlin_filemanager.image.imagefiles.ImageFileFragment


class AudioFolderFragment : Fragment(R.layout.fragment_audio_folder),FolderRecyclerViewAdapter.FolderInterface,IAudioFolderView {
    private var _binding : FragmentAudioFolderBinding? = null
    private val binding get() = _binding!!
    private var iAudioFolderPresenter : IAudioFolderPresenter? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAudioFolderBinding.inflate(inflater,container,false)
        iAudioFolderPresenter = AudioFolderPresenter(this)
        iAudioFolderPresenter!!.inti()
        initRecycleview()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    override val count: Int
        get() = iAudioFolderPresenter!!.getFolderImageList()!!.size

    override fun file(position: Int): String {
       return iAudioFolderPresenter!!.getFolderImageList()!![position]
    }

    override fun onClickItem(position: Int) {
        val indexPath = iAudioFolderPresenter!!.getFolderImageList()!![position].lastIndexOf("/")
        val nameOFFolder = iAudioFolderPresenter!!.getFolderImageList()!![position].substring(indexPath + 1)
        val audioFileFragment = AudioFileFragment()
        val bundle = Bundle()
        bundle.putString("nameOFFolder", nameOFFolder)
        audioFileFragment.arguments = bundle
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentMain, audioFileFragment)
        fragmentTransaction.addToBackStack(AudioFileFragment.TAG)
        fragmentTransaction.commit()
    }

    override fun getActivityAudioFolder(): Activity? {
        return activity
    }

    override fun initRecycleview() {
        val adapter = FolderRecyclerViewAdapter(this)
        binding.lvFolderAudio.adapter = adapter
        binding.lvFolderAudio.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter.notifyDataSetChanged()
    }
    companion object {
        val TAG: String = AudioFolderFragment::class.java.name
    }

}