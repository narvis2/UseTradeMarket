package com.example.usetrademarket.domain.repository

import com.example.usetrademarket.domain.model.GalleryPhoto

interface GalleryPhotoRepository {

    suspend fun getAllPhotos(): MutableList<GalleryPhoto>

}