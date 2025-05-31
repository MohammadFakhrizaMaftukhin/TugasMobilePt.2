package com.example.multiplerv

data class Product(
    val name: String,
    var count : Int,
    val price : Int,
    var isFavorite: Boolean = false,
    val imageResId: Int
)
