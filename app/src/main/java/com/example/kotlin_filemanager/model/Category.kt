package com.example.kotlin_filemanager.model

import java.io.Serializable

data class Category(var id: Int, var name: String, val icon: Int, val storage: Int) : Serializable