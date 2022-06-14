package com.example.kotlin_filemanager.image.imagefiles

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.Formatter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_filemanager.R
import com.example.kotlin_filemanager.databinding.FragmentImageFileBinding
import com.example.kotlin_filemanager.image.imageplayer.ImagePlayerFragment
import com.example.kotlin_filemanager.model.FileItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.time.Instant
import java.time.ZoneId

class ImageFileFragment : Fragment(R.layout.fragment_image_file),ImageFilesAdapter.ImageFilesInterface,
    IImageFileView {
    private var _binding : FragmentImageFileBinding? = null
    private val binding get() = _binding!!
    private var bottomSheetDialog: BottomSheetDialog? = null
    private var iImageFolderPresenter : IImageFilePresenter? = null
    private var imagesAdapter : ImageFilesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentImageFileBinding.inflate(inflater,container,false)
        val folder_name = arguments?.getString("nameOFFolder")
        iImageFolderPresenter = ImageFilesPresenter(folder_name,this)
        iImageFolderPresenter!!.init()
        binding.tvImageFolder.text = folder_name
        initView()
        binding.btnBack.setOnClickListener {
            if(requireActivity().supportFragmentManager != null){
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        return binding.root
    }

    companion object {
        val TAG: String = ImageFileFragment::class.java.name
    }

    override val count: Int
        get() = iImageFolderPresenter!!.getFilesImageList()!!.size

    override fun image(position: Int): FileItem {
       return iImageFolderPresenter!!.getFilesImageList()!![position]
    }

    override fun onClickItem(position: Int) {
        val imagePlayerFragment = ImagePlayerFragment()
        val bundle = Bundle()
        bundle.putSerializable("anh", iImageFolderPresenter!!.getFilesImageList()!![position])
        imagePlayerFragment.arguments = bundle
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentMain, imagePlayerFragment)
        fragmentTransaction.addToBackStack(ImagePlayerFragment.TAG)
        fragmentTransaction.commit()
    }

    override fun onLongClickItem(position: Int, v: View?): Boolean {
        val popupMenu = PopupMenu(requireContext(), v!!)
        popupMenu.menu.add("OPEN")
        popupMenu.menu.add("DELETE")
        popupMenu.menu.add("DETAIL")
        popupMenu.menu.add("SHARE")
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            if (item.title == "OPEN") {
                val imagePlayerFragment = ImagePlayerFragment()
                val bundle = Bundle()
                bundle.putSerializable("anh",
                    iImageFolderPresenter!!.getFilesImageList()!![position]
                )
                imagePlayerFragment.arguments = bundle
                val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentMain, imagePlayerFragment)
                fragmentTransaction.addToBackStack(ImagePlayerFragment.TAG)
                fragmentTransaction.commit()
            }
            if (item.title == "DELETE") {
                val alerDialog = AlertDialog.Builder(requireContext())
                alerDialog.setTitle("Deleta")
                alerDialog.setMessage("Do you want to delete this video ?")
                alerDialog.setPositiveButton("Delete") { dialog: DialogInterface?, which: Int ->
                    val uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        iImageFolderPresenter!!.getFilesImageList()!![position].id.toLong()
                    )
                    val file = File(iImageFolderPresenter!!.getFilesImageList()!![position].path)
                    val delete = file.delete()
                    if (delete) {
                        requireActivity().contentResolver.delete(uri, null, null)
                        iImageFolderPresenter!!.getFilesImageList()!!.removeAt(position)
                        imagesAdapter!!.notifyItemRemoved(position)
                        imagesAdapter!!.notifyItemRangeChanged(position, iImageFolderPresenter!!.getFilesImageList()!!.size)
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Can't deleted", Toast.LENGTH_SHORT).show()
                    }
                }
                alerDialog.setNegativeButton("Cancel") { dialog: DialogInterface, which: Int -> dialog.dismiss() }
                alerDialog.show()
            }
            if (item.title == "DETAIL") {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Information")
                val file = iImageFolderPresenter!!.getFilesImageList()!![position]
                val longTime = file.dateAdded.toLong() * 1000
                builder.setMessage(
                    """
    Name :${file.displayName}
    Size :${
                        Formatter.formatFileSize(context, iImageFolderPresenter!!.getFilesImageList()!![position].size.toLong())
                    }
    Date :${convertEpouch(longTime)}
    """.trimIndent()
                )
                builder.setPositiveButton("OK") { dialog: DialogInterface, which: Int -> dialog.cancel() }
                val al = builder.create()
                al.show()
            }
            if (item.title == "SHARE") {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                val body = "Download this file"
                val sub = "http://play.google.com"
                intent.putExtra(Intent.EXTRA_TEXT, body)
                intent.putExtra(Intent.EXTRA_TEXT, sub)
                startActivity(Intent.createChooser(intent, "Share using "))
            }
            true
        }
        popupMenu.show()
        return true
    }


    override fun onClickMenu(position: Int) {
        bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetTheme)
        val bsView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout,
            requireActivity().findViewById(R.id.bottom_sheet))
        bsView.findViewById<View>(R.id.bs_language).setOnClickListener {
        bottomSheetDialog!!.dismiss() }
        bottomSheetDialog!!.setContentView(bsView)
        bottomSheetDialog!!.show()
    }

    override fun context(): Context? {
        return  context
    }

    override fun getActivityImageFile(): Activity? {
        return activity
    }

    override fun initView() {
        imagesAdapter = ImageFilesAdapter(this)
        binding.lvListItem.adapter = imagesAdapter
        binding.lvListItem.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun convertEpouch(epouch: Long): String {
        val ld = Instant.ofEpochMilli(epouch).atZone(ZoneId.systemDefault()).toLocalDate()
        return ld.toString()
    }
}