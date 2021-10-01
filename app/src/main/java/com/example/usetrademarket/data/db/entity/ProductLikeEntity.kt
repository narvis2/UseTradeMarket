package com.example.usetrademarket.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductLikeEntity(
    @PrimaryKey
    val id : Long,
    val name : String,
    val description : String,
    val price : Int,
    val status : String,
    val sellerId : Long,
    val imagePaths : String
)
