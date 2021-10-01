package com.example.usetrademarket.domain.usecase

import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.data.model.response.ProductResponse
import com.example.usetrademarket.domain.repository.UseTradeRepository

class GetProductUseCase(
    private val useTradeRepository: UseTradeRepository
) {

    suspend fun execute(productId: Long) : ApiResponse<ProductResponse> {
        return useTradeRepository.getProduct(productId)
    }
}