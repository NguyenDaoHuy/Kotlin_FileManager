package com.example.kotlin_filemanager.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class FileItem (var id : String,
                     var title : String,
                     var displayName : String,
                     var size : String,
                     var duration : String?,
                     var path : String,
                     var dateAdded : String) : Serializable,Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<FileItem> {
        override fun createFromParcel(parcel: Parcel): FileItem {
            return FileItem(parcel)
        }

        override fun newArray(size: Int): Array<FileItem?> {
            return arrayOfNulls(size)
        }
    }
}