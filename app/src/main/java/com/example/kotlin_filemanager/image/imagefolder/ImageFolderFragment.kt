package com.example.kotlin_filemanager.image.imagefolder

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
import com.example.kotlin_filemanager.databinding.FragmentImageFolderBinding
import com.example.kotlin_filemanager.image.imagefiles.ImageFileFragment

class ImageFolderFragment : Fragment(R.layout.fragment_image_folder),FolderRecyclerViewAdapter.FolderInterface,
    IImageFolderView {
    private var _binding : FragmentImageFolderBinding? = null
    private val binding get() = _binding!!
    private var iImageFolderPresenter : IImageFolderPresenter? = null


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentImageFolderBinding.inflate(inflater,container,false)
        iImageFolderPresenter = ImageFolderPresenter(this)
        iImageFolderPresenter!!.inti()
        initRecycleview()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
             val intent = Intent(activity,MainActivity::class.java)
             activity?.startActivity(intent)
        }
    }

    override val count: Int
        get() = iImageFolderPresenter!!.getFolderImageList()!!.size

    override fun file(position: Int): String? {
          return iImageFolderPresenter?.getFolderImageList()?.get(position)
    }

    override fun onClickItem(position: Int) {
        val indexPath = iImageFolderPresenter!!.getFolderImageList()!![position].lastIndexOf("/")
        val nameOFFolder = iImageFolderPresenter!!.getFolderImageList()!![position].substring(indexPath + 1)
        val imageFilesFragment = ImageFileFragment()
        val bundle = Bundle()
        bundle.putString("nameOFFolder", nameOFFolder)
        imageFilesFragment.arguments = bundle
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentMain, imageFilesFragment)
        fragmentTransaction.addToBackStack(ImageFileFragment.TAG)
        fragmentTransaction.commit()
    }

    override fun notData(str: String?) {

    }

    override fun getActivityImageFolder(): Activity?{
        return activity
    }

    override fun initRecycleview() {
        val adapter = FolderRecyclerViewAdapter(this)
        binding.lvFolderImage.adapter = adapter
        binding.lvFolderImage.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter.notifyDataSetChanged()
    }
}