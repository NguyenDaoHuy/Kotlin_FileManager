package com.example.kotlin_filemanager.documents.newfiles

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_filemanager.MainActivity
import com.example.kotlin_filemanager.R
import com.example.kotlin_filemanager.databinding.FragmentNewFilesFlagmentBinding
import com.example.kotlin_filemanager.documents.documentfiles.DocumentsFileAdapter
import com.example.kotlin_filemanager.documents.documentfiles.IDocumentFileView
import com.example.kotlin_filemanager.model.Item
import java.io.File


class NewFilesFlagment : Fragment(R.layout.fragment_new_files_flagment),
    DocumentsFileAdapter.DocumentFileInterface,IDocumentFileView {
    private lateinit var _binding: FragmentNewFilesFlagmentBinding
    private val binding get() = _binding
    private lateinit var iNewFilesPresenter: INewFilesPresenter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNewFilesFlagmentBinding.inflate(inflater, container, false)
        iNewFilesPresenter = NewFilesPresenter()
        iNewFilesPresenter.inti()
        initRecycleview()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    override val count: Int
        get() = iNewFilesPresenter.getNewFileList()!!.size

    override fun item(position: Int): Item {
        return iNewFilesPresenter.getNewFileList()!![position]
    }

    override fun onClickItem(position: Int) {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.action = Intent.ACTION_VIEW
        val file = File(iNewFilesPresenter.getNewFileList()!![position].path)
        val extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString())
        val mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        val photoURI = FileProvider.getUriForFile(binding.root.context, binding.root.context.packageName + ".provider", file)
        if (extension.equals("", ignoreCase = true) || mimetype == null) {
            intent.setDataAndType(photoURI, "text/*")
        } else {
            intent.setDataAndType(photoURI, mimetype)
        }
        startActivity(intent)
    }

    override fun initRecycleview() {
        val adapter = DocumentsFileAdapter(this)
        binding.rvNewFile.adapter = adapter
        binding.rvNewFile.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter.notifyDataSetChanged()
    }
}
