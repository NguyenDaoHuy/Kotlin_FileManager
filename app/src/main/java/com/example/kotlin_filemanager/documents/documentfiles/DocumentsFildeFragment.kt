package com.example.kotlin_filemanager.documents.documentfiles

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_filemanager.MainActivity
import com.example.kotlin_filemanager.R
import com.example.kotlin_filemanager.databinding.FragmentVideoFolderBinding
import com.example.kotlin_filemanager.image.imagefiles.ImageFileFragment
import com.example.kotlin_filemanager.model.Item
import java.io.File

class DocumentsFildeFragment : Fragment(R.layout.fragment_documents_filde) , DocumentsFileAdapter.DocumentFileInterface,IDocumentFileView {
    private var _binding : FragmentVideoFolderBinding? = null
    private val binding get() = _binding!!
    private var idocumentsFilePresenter : IDocumentFilePresenter? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVideoFolderBinding.inflate(inflater,container,false)
        idocumentsFilePresenter = DocumentsFilePresenterI()
        idocumentsFilePresenter!!.inti()
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
        get() = idocumentsFilePresenter!!.getDocumenFileList()!!.size

    override fun item(position: Int): Item? {
        return idocumentsFilePresenter!!.getDocumenFileList()!![position]
    }

    override fun onClickItem(position: Int) {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.action = Intent.ACTION_VIEW
        val file = File(idocumentsFilePresenter!!.getDocumenFileList()!![position]!!.path)
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
        binding.lvFolderVideo.adapter = adapter
        binding.lvFolderVideo.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter.notifyDataSetChanged()
    }
    companion object {
        val TAG: String = DocumentsFildeFragment::class.java.name
    }
}