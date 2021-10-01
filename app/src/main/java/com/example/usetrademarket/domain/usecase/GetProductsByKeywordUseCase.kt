package com.example.usetrademarket.domain.usecase

import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.data.model.response.ProductListItemResponse
import com.example.usetrademarket.domain.repository.UseTradeRepository

class GetProductsByKeywordUseCase(
    private val useTradeRepository: UseTradeRepository
) {

    suspend fun execute(
        productId : Long,
        categoryId : Int?,
        direction: String, // prev, next
        keyword: String?
    ) : ApiResponse<List<ProductListItemResponse>> {
        return useTradeRepository.getProductsByKeyword(productId, categoryId, direction, keyword)
    }
}