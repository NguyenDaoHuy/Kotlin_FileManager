package com.example.kotlin_filemanager.image.imagefiles

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import android.content.Context
import android.graphics.BitmapFactory
import android.text.format.Formatter
import android.view.View
import com.example.kotlin_filemanager.R
import com.example.kotlin_filemanager.databinding.ItemListFilesBinding
import com.example.kotlin_filemanager.model.FileItem

class ImageFilesAdapter(private val imageFilesInterface: ImageFilesInterface) :
    RecyclerView.Adapter<ImageFilesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemListFilesBinding>(layoutInflater, R.layout.item_list_files, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = imageFilesInterface.image(position)
        holder.binding.imgAnh.setImageBitmap(BitmapFactory.decodeFile(image!!.path))
        holder.binding.sizeAnh.text = Formatter.formatFileSize(holder.binding.sizeAnh.context, image.size.toLong())
        holder.binding.tenAnh.text = image.displayName
        holder.binding.btnMenu.setOnClickListener {
            imageFilesInterface.onClickMenu(position)
        }
        holder.itemView.setOnClickListener {
            imageFilesInterface.onClickItem(position)
        }
        holder.itemView.setOnLongClickListener { v: View? ->
            imageFilesInterface.onLongClickItem(position, v)
        }
    }

    override fun getItemCount(): Int {
        return imageFilesInterface.count
    }

    class ViewHolder(var binding: ItemListFilesBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    interface ImageFilesInterface {
        val count: Int
        fun image(position: Int): FileItem?
        fun onClickItem(position: Int)
        fun onLongClickItem(position: Int, v: View?): Boolean
        fun onClickMenu(position: Int)
        fun context(): Context?
    }
}