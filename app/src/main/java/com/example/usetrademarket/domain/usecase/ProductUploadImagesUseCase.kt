package com.example.usetrademarket.domain.usecase

import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.data.model.response.ProductImageUploadResponse
import com.example.usetrademarket.domain.repository.UseTradeRepository
import okhttp3.MultipartBody

class ProductUploadImagesUseCase(
    private val useTradeRepository: UseTradeRepository
) {

    suspend fun execute(images: MultipartBody.Part) : ApiResponse<ProductImageUploadResponse> {
        return useTradeRepository.uploadProductImages(images)
    }
}