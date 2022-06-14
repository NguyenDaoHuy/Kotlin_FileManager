package com.example.kotlin_filemanager.documents.documentfiles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_filemanager.R
import com.example.kotlin_filemanager.databinding.FolderItemBinding
import com.example.kotlin_filemanager.model.Item

class DocumentsFileAdapter(private val documentFileInterface : DocumentFileInterface) :
    RecyclerView.Adapter<DocumentsFileAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInterface = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<FolderItemBinding>(layoutInterface, R.layout.folder_item,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val i = documentFileInterface.item(position)
        holder.binding.folderName.text = i!!.name
        if (i.name.endsWith(".docx")) {
            holder.binding.imgFolder.setImageResource(R.drawable.icon_word)
        } else if (i.name.endsWith(".txt")) {
            holder.binding.imgFolder.setImageResource(R.drawable.icon_txt)
        } else if (i.name.endsWith(".xls")) {
            holder.binding.imgFolder.setImageResource(R.drawable.icon_exel)
        } else if (i.name.endsWith(".pdf")) {
            holder.binding.imgFolder.setImageResource(R.drawable.icon_pdf)
        } else if (i.name.endsWith(".pptx")) {
            holder.binding.imgFolder.setImageResource(R.drawable.icon_ppt)
        } else if (i.name.endsWith(".mp3")) {
            holder.binding.imgFolder.setImageResource(R.drawable.icons8_sing)
        }
        holder.itemView.setOnClickListener {
            documentFileInterface.onClickItem(position)
        }
    }

    override fun getItemCount(): Int {
        return documentFileInterface.count
    }

    class ViewHolder(var binding : FolderItemBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    interface DocumentFileInterface {
        val count: Int
        fun item(position: Int): Item?
        fun onClickItem(position: Int)
    }
}