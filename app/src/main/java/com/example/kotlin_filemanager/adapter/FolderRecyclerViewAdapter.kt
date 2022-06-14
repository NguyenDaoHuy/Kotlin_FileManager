package com.example.kotlin_filemanager.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import android.view.View
import com.example.kotlin_filemanager.R
import com.example.kotlin_filemanager.databinding.FolderItemBinding

class FolderRecyclerViewAdapter(private val imageFolderInterface: FolderInterface) :
    RecyclerView.Adapter<FolderRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<FolderItemBinding>(layoutInflater, R.layout.folder_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val indexPath = imageFolderInterface.file(position)!!.lastIndexOf("/")
        val nameOFFolder = imageFolderInterface.file(position)!!.substring(indexPath + 1)
        holder.binding.folderName.text = nameOFFolder
        holder.binding.folderPath.text = imageFolderInterface.file(position)
        holder.itemView.setOnClickListener { imageFolderInterface.onClickItem(position) }
    }

    override fun getItemCount(): Int {
        return imageFolderInterface.count
    }

    class ViewHolder(var binding: FolderItemBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    interface FolderInterface {
        val count: Int
        fun file(position: Int): String?
        fun onClickItem(position: Int)
    }
}