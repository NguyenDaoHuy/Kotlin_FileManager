package com.example.kotlin_filemanager.video.videofiles

import android.annotation.SuppressLint
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
import com.example.kotlin_filemanager.databinding.FragmentVideoFilesBinding
import com.example.kotlin_filemanager.image.imageplayer.ImagePlayerFragment
import com.example.kotlin_filemanager.model.FileItem
import com.example.kotlin_filemanager.video.videoplayer.VideoPlayerFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.time.Instant
import java.time.ZoneId

class VideoFilesFragment : Fragment(R.layout.fragment_video_files),IVideoFileView,
    VideoListFilesAdapter.VideoFilesInterface {
    private var _binding : FragmentVideoFilesBinding? = null
    private val binding get() = _binding!!
    private var iVideoFilePresenter : IVideoFilePresenter? = null
    private var videoAdapter : VideoListFilesAdapter? = null
    private var bottomSheetDialog: BottomSheetDialog? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVideoFilesBinding.inflate(inflater,container,false)
        val folder_name = arguments?.getString("nameOFFolder")
        iVideoFilePresenter = VideoFilePressenter(folder_name,this)
        iVideoFilePresenter!!.init()
        onClick()
        binding.tvVideoFolder.text = folder_name
        initRecycleview()
        return binding.root
    }
    companion object {
        val TAG: String = VideoFilesFragment::class.java.name
    }

    override fun getActivityVideoFile(): Activity? {
        return activity
    }

    override fun initRecycleview() {
        videoAdapter = VideoListFilesAdapter(this)
        binding.videoRv.adapter = videoAdapter
        binding.videoRv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        videoAdapter!!.notifyDataSetChanged()
    }

    private fun onClick() {
        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override val count: Int
        get() = iVideoFilePresenter!!.getFilesVideoList()!!.size

    override fun file(position: Int): FileItem {
        return iVideoFilePresenter!!.getFilesVideoList()!![position]
    }

    override fun onClickItem(position: Int) {
        val videoPlayerFragment = VideoPlayerFragment()
        val bundle = Bundle()
        bundle.putInt("position", position)
        bundle.putString("video_title", iVideoFilePresenter!!.getFilesVideoList()!![position].displayName)
        bundle.putParcelableArrayList("videoArrayList",iVideoFilePresenter!!.getFilesVideoList()!!)
        videoPlayerFragment.arguments = bundle
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentMain, videoPlayerFragment)
        fragmentTransaction.addToBackStack(VideoPlayerFragment.TAG)
        fragmentTransaction.commit()
    }

    override fun onLongClick(position: Int, v: View?): Boolean {
        val popupMenu = PopupMenu(requireContext(), v!!)
        popupMenu.menu.add("OPEN")
        popupMenu.menu.add("DELETE")
        popupMenu.menu.add("DETAIL")
        popupMenu.menu.add("SHARE")
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            if (item.title == "OPEN") {
                val imagePlayerFragment = ImagePlayerFragment()
                val bundle = Bundle()
                bundle.putSerializable("anh", iVideoFilePresenter!!.getFilesVideoList()!![position])
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
                        iVideoFilePresenter!!.getFilesVideoList()!![position].id.toLong()
                    )
                    val file = File(iVideoFilePresenter!!.getFilesVideoList()!![position].path)
                    val delete = file.delete()
                    if (delete) {
                        requireActivity().contentResolver.delete(uri, null, null)
                        iVideoFilePresenter!!.getFilesVideoList()!!.removeAt(position)
                        videoAdapter!!.notifyItemRemoved(position)
                        videoAdapter!!.notifyItemRangeChanged(position, iVideoFilePresenter!!.getFilesVideoList()!!.size)
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
                val file = iVideoFilePresenter!!.getFilesVideoList()!![position]
                val longTime = file.dateAdded.toLong() * 1000
                builder.setMessage(
                    """
                  Name :${file.displayName}
                  Size :${
                        Formatter.formatFileSize(context, iVideoFilePresenter!!.getFilesVideoList()!![position].size.toLong())
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

    override fun onMenuClick(position: Int) {
        bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetTheme)
        val bsView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_layout,
            requireActivity().findViewById(R.id.bottom_sheet))
        bsView.findViewById<View>(R.id.bs_language).setOnClickListener {
        bottomSheetDialog!!.dismiss() }
        bottomSheetDialog!!.setContentView(bsView)
        bottomSheetDialog!!.show()
    }

    private fun convertEpouch(epouch: Long): String {
        val ld = Instant.ofEpochMilli(epouch).atZone(ZoneId.systemDefault()).toLocalDate()
        return ld.toString()
    }

    override fun context(): Context? {
        return  context
    }
}