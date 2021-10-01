package com.example.usetrademarket.domain.usecase

import com.example.usetrademarket.data.db.entity.ProductLikeEntity
import com.example.usetrademarket.domain.repository.UseTradeRepository
import kotlinx.coroutines.flow.Flow

class GetAllUseLikeProductUseCase(
    private val useTradeRepository: UseTradeRepository
) {
    fun execute() : Flow<List<ProductLikeEntity>> =
        useTradeRepository.getAllUserLikedProduct()
}