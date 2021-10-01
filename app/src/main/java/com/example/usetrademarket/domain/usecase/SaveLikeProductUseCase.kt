package com.example.usetrademarket.domain.usecase

import com.example.usetrademarket.data.db.entity.ProductLikeEntity
import com.example.usetrademarket.domain.repository.UseTradeRepository

class SaveLikeProductUseCase(
    private val useTradeRepository: UseTradeRepository
) {

    suspend fun execute(productLikeEntity: ProductLikeEntity) {
        useTradeRepository.insert(productLikeEntity)
    }
}