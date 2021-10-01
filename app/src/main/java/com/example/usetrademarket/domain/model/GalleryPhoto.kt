package com.example.usetrademarket.domain.model

import android.net.Uri

data class GalleryPhoto(
    val id: Long,
    val uri: Uri,
    val name: String,
    val date: String,
    val size: Int,
    var isSelected: Boolean = false
)