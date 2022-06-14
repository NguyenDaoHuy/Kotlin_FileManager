package com.example.kotlin_filemanager.image.imageplayer

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin_filemanager.R
import com.example.kotlin_filemanager.databinding.FragmentImagePlayerBinding
import com.example.kotlin_filemanager.model.FileItem

class ImagePlayerFragment : Fragment(R.layout.fragment_image_player),IImagePlayerView {
    private var _binding : FragmentImagePlayerBinding? = null
    private val binding get() = _binding!!
    private var iImageFolderPresenter : IImagePlayerPresenter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentImagePlayerBinding.inflate(inflater,container,false)
        iImageFolderPresenter = ImagePlayerPresenter(this)
        iImageFolderPresenter!!.init()
        return binding.root
    }
    companion object {
        val TAG = ImagePlayerFragment::class.java.name
    }

    override fun displayImage() {
        val fileDevices = arguments?.getSerializable("anh") as FileItem?
        val str = fileDevices!!.path
        binding!!.imgView.setImageBitmap(BitmapFactory.decodeFile(str))
    }

    override fun onClick() {
        binding.btnBack.setOnClickListener {
            if(requireActivity().supportFragmentManager != null){
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        binding.btnShare.setOnClickListener { v: View? ->
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            val body = "Download this file"
            val sub = "http://play.google.com"
            intent.putExtra(Intent.EXTRA_TEXT, body)
            intent.putExtra(Intent.EXTRA_TEXT, sub)
            startActivity(Intent.createChooser(intent, "Share using "))
        }
    }
}