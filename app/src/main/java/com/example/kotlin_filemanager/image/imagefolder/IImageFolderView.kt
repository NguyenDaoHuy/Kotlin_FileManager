package com.example.kotlin_filemanager.image.imagefolder

import android.app.Activity

interface IImageFolderView {
     fun notData(str : String?)
     fun getActivityImageFolder() : Activity?
     fun initRecycleview()
}