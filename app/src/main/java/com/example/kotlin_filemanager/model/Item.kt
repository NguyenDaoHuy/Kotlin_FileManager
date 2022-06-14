package com.example.kotlin_filemanager.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Item(var name : String,var path : String) :Serializable,Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}