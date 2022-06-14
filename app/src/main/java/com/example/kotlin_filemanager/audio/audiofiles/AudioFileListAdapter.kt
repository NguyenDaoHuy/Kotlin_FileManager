package com.example.kotlin_filemanager.audio.audiofiles

import android.content.Context
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_filemanager.R
import com.example.kotlin_filemanager.databinding.AudioItemBinding
import com.example.kotlin_filemanager.model.FileItem
import com.example.kotlin_filemanager.video.videofiles.VideoListFilesAdapter

class AudioFileListAdapter(private val audioFileInterface: AudioFileInterface):
    RecyclerView.Adapter<AudioFileListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<AudioItemBinding>(layoutInflater, R.layout.audio_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val audio = audioFileInterface.audio(position)
        holder.binding.audioName.text = audio!!.displayName
        holder.binding.audioSize.text = Formatter.formatFileSize(holder.binding.audioSize.context, audio.size.toLong())
        holder.binding.tvTimeAudio.text = VideoListFilesAdapter.convertToMMSS(audio.duration.toString())
        holder.itemView.setOnClickListener {
             audioFileInterface.onClickItem(position)
        }
    }

    override fun getItemCount(): Int {
        return audioFileInterface.count
    }
    class ViewHolder(var binding: AudioItemBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    interface AudioFileInterface {
        val count: Int
        fun audio(position: Int): FileItem?
        fun onClickItem(position: Int)
        fun context(): Context?
    }

}