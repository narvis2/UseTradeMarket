package com.example.usetrademarket.domain.usecase

import com.example.usetrademarket.domain.model.GalleryPhoto
import com.example.usetrademarket.domain.repository.GalleryPhotoRepository

class GetAllPhotosUseCase(
    private val galleryPhotoRepository: GalleryPhotoRepository
) {

    suspend fun execute() : MutableList<GalleryPhoto> {
        return galleryPhotoRepository.getAllPhotos()
    }

}