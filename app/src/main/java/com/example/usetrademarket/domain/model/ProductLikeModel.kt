package com.example.usetrademarket.domain.model

data class ProductLikeModel(
    val id : Long,
    val name : String,
    val description : String,
    val price : Int,
    val status : String,
    val sellerId : Long,
    val imagePaths : String
)
