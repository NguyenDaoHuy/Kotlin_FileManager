package com.example.kotlin_filemanager

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_filemanager.adapter.CategoryAdapter
import com.example.kotlin_filemanager.audio.audiofolder.AudioFolderFragment
import com.example.kotlin_filemanager.databinding.ActivityMainBinding
import com.example.kotlin_filemanager.documents.documentfiles.DocumentsFildeFragment
import com.example.kotlin_filemanager.documents.newfiles.NewFilesFlagment
import com.example.kotlin_filemanager.image.imagefolder.ImageFolderFragment
import com.example.kotlin_filemanager.model.Category
import com.example.kotlin_filemanager.video.videofolder.VideoFolderFragment

@Suppress("ControlFlowWithEmptyBody")
class MainActivity : AppCompatActivity(),CategoryAdapter.CategoryInterFace {
    private var categoryArrayList : ArrayList<Category>? = null
    private var binding : ActivityMainBinding? = null
    private val mActivity : Activity = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        displayCategory()
        connectPermission()
    }

    private fun displayCategory() {
        categoryArrayList = ArrayList()
        categoryArrayList!!.add(Category(1, "Images", R.drawable.icons_image, 10))
        categoryArrayList!!.add(Category(2, "Audio", R.drawable.icons_new_files, 10))
        categoryArrayList!!.add(Category(3, "Videos", R.drawable.icons_video, 10))
        categoryArrayList!!.add(Category(4, "Documents", R.drawable.icons8_documents, 10))
        categoryArrayList!!.add(Category(5, "Apps", R.drawable.icons_android, 120))
        categoryArrayList!!.add(Category(6, "New files", R.drawable.icon_file, 10))
        categoryArrayList!!.add(Category(7, "Cloud", R.drawable.icons_cloud, 120))
        categoryArrayList!!.add(Category(8, "Remote", R.drawable.icons8_remote, 120))
        categoryArrayList!!.add(Category(9, "Access device", R.drawable.icons_remote, 120))
        val categoryAdapter = CategoryAdapter(this)
        val layoutManager :RecyclerView.LayoutManager =GridLayoutManager(applicationContext,3,RecyclerView.VERTICAL, false)
        binding!!.rvDanhMuc.layoutManager = layoutManager
        binding!!.rvDanhMuc.adapter = categoryAdapter
    }
    private fun connectPermission() {
        if (checkPermission()) {

        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("App Permission")
                .setMessage("""
                     You must allow this aoo to access files on your device
    
                     Now follow the below steps
    
                     Open Settings from below button
                     Click on Permission
                     Allow access for storage
                     """.trimIndent()
                )
                .setPositiveButton("Open Settings") { dialog: DialogInterface?, which: Int -> requestPermission() }
                .create().show()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Log.d(TAG, "requestPermission: try")
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package", this.packageName, null)
                intent.data = uri
                storageActivityResultLauncher.launch(intent)
            } catch (e: Exception) {
                Log.d(TAG, "requestPermission: catch")
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                storageActivityResultLauncher.launch(intent)
            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION)
        }
    }

    private val storageActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        result: ActivityResult? ->
        Log.d(TAG, "onActivityResult: ")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                Log.d(TAG, "onActivityResult: Manage External Storage Permission is granted")
                Toast.makeText(this@MainActivity, "Manage External Storage Permission is granted", Toast.LENGTH_SHORT).show()
                //hàm
                restartActivity(mActivity)
            }
        } else {
            Log.d(TAG, "onActivityResult: Manage External Storage Permission is denied")
            Toast.makeText(this@MainActivity, "Manage External Storage Permission is denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE
            )
            write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty()) {
                val write = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (write) {
                    // hàm
                    restartActivity(mActivity)
                    Log.d(TAG, "onRequestPermissionResult: External Storage permission granted")
                } else {
                    Log.d(TAG, "onRequestPermissionResult: External Storage permission denied")
                    Toast.makeText(
                        this@MainActivity,
                        "External Storage permission denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override val count: Int
        get() = categoryArrayList!!.size

    override fun category(position: Int): Category {
        return categoryArrayList!![position]
    }

    override fun onClickItem(position: Int) {
        val category = categoryArrayList!![position]
        val id = category.id
        if (id == 1) {
            getFragment(ImageFolderFragment())
        } else if (id == 2) {
            getFragment(AudioFolderFragment())
        } else if (id == 3) {
            getFragment(VideoFolderFragment())
        } else if (id == 4) {
            getFragment(DocumentsFildeFragment())
        } else if (id == 5) {
            Toast.makeText(this, "Not data", Toast.LENGTH_SHORT).show()
        } else if (id == 6) {
            getFragment(NewFilesFlagment())
        } else if (id == 7) {
            Toast.makeText(this, "Not data", Toast.LENGTH_SHORT).show()
        } else if (id == 8) {
            Toast.makeText(this, "Not data", Toast.LENGTH_SHORT).show()
        } else if (id == 9) {
            Toast.makeText(this, "Not data", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getFragment(fragment: Fragment?) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentMain, fragment!!)
        fragmentTransaction.commit()
    }
    companion object {
        private const val STORAGE_PERMISSION = 100
        private const val TAG = "PERMISSON_TAG"
        fun restartActivity(activity: Activity) {
            activity.recreate()
        }
    }
}