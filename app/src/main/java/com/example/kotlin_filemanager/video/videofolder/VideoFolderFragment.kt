package com.example.kotlin_filemanager.video.videofolder

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
import com.example.kotlin_filemanager.databinding.FragmentVideoFolderBinding
import com.example.kotlin_filemanager.video.videofiles.VideoFilesFragment


class VideoFolderFragment : Fragment(R.layout.fragment_video_folder),FolderRecyclerViewAdapter.FolderInterface,IVideoFolderView {

    private var _binding : FragmentVideoFolderBinding? = null
    private val binding get() = _binding!!
    private var iVideoFolderPresenter : IVideoFolderPresenter? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVideoFolderBinding.inflate(inflater,container,false)
        iVideoFolderPresenter = VideoFolderPresenter(this)
        iVideoFolderPresenter!!.inti()
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
        get() = iVideoFolderPresenter!!.getFolderAudioList()!!.size

    override fun file(position: Int): String {
        return iVideoFolderPresenter!!.getFolderAudioList()!![position]
    }

    override fun onClickItem(position: Int) {
        val indexPath = iVideoFolderPresenter!!.getFolderAudioList()!![position].lastIndexOf("/")
        val nameOFFolder = iVideoFolderPresenter!!.getFolderAudioList()!![position].substring(indexPath + 1)
        val videoFileFragment = VideoFilesFragment()
        val bundle = Bundle()
        bundle.putString("nameOFFolder", nameOFFolder)
        videoFileFragment.arguments = bundle
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentMain, videoFileFragment)
        fragmentTransaction.addToBackStack(VideoFilesFragment.TAG)
        fragmentTransaction.commit()
    }

    override fun notData(str: String?) {

    }

    override fun getActivityImageFolder(): Activity? {
        return activity
    }

    override fun initRecycleview() {
        val adapter = FolderRecyclerViewAdapter(this)
        binding.lvFolderVideo.adapter = adapter
        binding.lvFolderVideo.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter.notifyDataSetChanged()
    }

}