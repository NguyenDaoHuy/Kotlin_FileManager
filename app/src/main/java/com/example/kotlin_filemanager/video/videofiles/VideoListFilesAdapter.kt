package com.example.kotlin_filemanager.video.videofiles

import android.content.Context
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin_filemanager.R
import com.example.kotlin_filemanager.databinding.VideoItemBinding
import com.example.kotlin_filemanager.model.FileItem
import java.io.File
import java.util.concurrent.TimeUnit

class VideoListFilesAdapter(private val videoFilesInterface: VideoFilesInterface) : RecyclerView.Adapter<VideoListFilesAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<VideoItemBinding>(layoutInflater, R.layout.video_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = videoFilesInterface.file(position)
        Glide.with(holder.binding.thumbnail.context).load(File(video!!.path)).into(holder.binding.thumbnail)
        holder.binding.videoDuration.text = convertToMMSS(video.duration.toString())
        holder.binding.videoName.text = video.displayName
        holder.binding.videoSize.text = Formatter.formatFileSize(holder.binding.videoSize.context, video.size.toLong())

        holder.binding.btnMenu2.setOnClickListener { v: View? ->
            videoFilesInterface.onMenuClick(position)
        }
        holder.itemView.setOnClickListener { v: View? ->
            videoFilesInterface.onClickItem(position)
        }
        holder.itemView.setOnLongClickListener { v: View? ->
            videoFilesInterface.onLongClick(position, v)
        }
    }

    override fun getItemCount(): Int {
        return videoFilesInterface.count
    }
    class ViewHolder(var binding : VideoItemBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    interface VideoFilesInterface {
        val count: Int
        fun file(position: Int): FileItem?
        fun onClickItem(position: Int)
        fun onLongClick(position: Int, v: View?): Boolean
        fun onMenuClick(position: Int)
        fun context(): Context?
    }
    companion object {
        fun convertToMMSS(duration: String): String {
            val millis = duration.toLong()
            return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
            )
        }
    }
}