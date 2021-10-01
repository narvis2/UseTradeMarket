package com.example.usetrademarket.domain.usecase

import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.data.model.response.ProductListItemResponse
import com.example.usetrademarket.domain.repository.UseTradeRepository
import retrofit2.http.Query

class GetProductsListUseCase(
    private val useTradeRepository: UseTradeRepository
) {

    suspend fun execute(
        productId : Long,
        categoryId : Int?,
        direction: String // prev, next
    ) : ApiResponse<List<ProductListItemResponse>> {
        return useTradeRepository.getProducts(productId, categoryId, direction)
    }
}