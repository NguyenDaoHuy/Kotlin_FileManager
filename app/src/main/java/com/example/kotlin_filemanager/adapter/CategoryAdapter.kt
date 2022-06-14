package com.example.kotlin_filemanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_filemanager.R
import com.example.kotlin_filemanager.databinding.ItemDanhMucBinding
import com.example.kotlin_filemanager.model.Category

class CategoryAdapter(private val categoryInterFace: CategoryInterFace) : RecyclerView.Adapter<CategoryAdapter.ViewHoder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ItemDanhMucBinding = DataBindingUtil.inflate(layoutInflater,R.layout.item_danh_muc,parent,false)
        return ViewHoder(binding)
    }

    override fun onBindViewHolder(holder: ViewHoder, position: Int) {
        val category = categoryInterFace.category(position)
        holder.binding.tvTenDanhMuc.text = category.name
        holder.binding.iconLogo.setImageResource(category.icon)
        holder.binding.executePendingBindings()
        holder.itemView.setOnClickListener { categoryInterFace.onClickItem(position) }
    }

    override fun getItemCount(): Int {
        return categoryInterFace.count
    }

    class ViewHoder(val binding: ItemDanhMucBinding) : RecyclerView.ViewHolder(binding.root)

    interface CategoryInterFace {
        val count: Int
        fun category(position: Int): Category
        fun onClickItem(position: Int)
    }
}