package com.example.kotlin_filemanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_filemanager.adapter.CategoryAdapter
import com.example.kotlin_filemanager.audio.audiofolder.AudioFolderFragment
import com.example.kotlin_filemanager.databinding.FragmentMainBinding
import com.example.kotlin_filemanager.documents.documentfiles.DocumentsFildeFragment
import com.example.kotlin_filemanager.documents.newfiles.NewFilesFlagment
import com.example.kotlin_filemanager.image.imagefiles.ImageFileFragment
import com.example.kotlin_filemanager.image.imagefolder.ImageFolderFragment
import com.example.kotlin_filemanager.model.Category
import com.example.kotlin_filemanager.video.videofolder.VideoFolderFragment

class MainFragment : Fragment(R.layout.fragment_main),CategoryAdapter.CategoryInterFace {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var categoryArrayList : ArrayList<Category>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        displayCategory()
        return binding.root
    }
    private fun displayCategory() {
        categoryArrayList = ArrayList()
        categoryArrayList!!.add(Category(1, "Images", R.drawable.icons_image, 10))
        categoryArrayList!!.add(Category(2, "Audio", R.drawable.icons_new_files, 10))
        categoryArrayList!!.add(Category(3, "Videos", R.drawable.icons_video, 10))
        categoryArrayList!!.add(Category(4, "Documents", R.drawable.icons8_documents, 10))
        categoryArrayList!!.add(Category(5, "Apps", R.drawable.icons_android, 120))
        categoryArrayList!!.add(Category(6, "New files", R.drawable.icon_file, 10))
        categoryArrayList!!.add(Category(7, "Cloud", R.drawable.icons_cloud, 120))
        categoryArrayList!!.add(Category(8, "Remote", R.drawable.icons8_remote, 120))
        categoryArrayList!!.add(Category(9, "Access device", R.drawable.icons_remote, 120))
        val categoryAdapter = CategoryAdapter(this)
        val layoutManager : RecyclerView.LayoutManager =
            GridLayoutManager(context,3, RecyclerView.VERTICAL, false)
        binding!!.rvDanhMuc.layoutManager = layoutManager
        binding!!.rvDanhMuc.adapter = categoryAdapter
    }

    override val count: Int
        get() = categoryArrayList!!.size

    override fun category(position: Int): Category {
        return categoryArrayList!![position]
    }

    override fun onClickItem(position: Int) {
        val category = categoryArrayList!![position]
        val id = category.id
        if (id == 1) {
            getFragment(ImageFolderFragment(),ImageFolderFragment.TAG)
        } else if (id == 2) {
            getFragment(AudioFolderFragment(),AudioFolderFragment.TAG)
        } else if (id == 3) {
            getFragment(VideoFolderFragment(),VideoFolderFragment.TAG)
        } else if (id == 4) {
            getFragment(DocumentsFildeFragment(),DocumentsFildeFragment.TAG)
        } else if (id == 5) {
            Toast.makeText(context, "Not data", Toast.LENGTH_SHORT).show()
        } else if (id == 6) {
            getFragment(NewFilesFlagment(),NewFilesFlagment.TAG)
        } else if (id == 7) {
            Toast.makeText(context, "Not data", Toast.LENGTH_SHORT).show()
        } else if (id == 8) {
            Toast.makeText(context, "Not data", Toast.LENGTH_SHORT).show()
        } else if (id == 9) {
            Toast.makeText(context, "Not data", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getFragment(fragment: Fragment?,tag: String?) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentMain, fragment!!)
        fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commit()
    }
}