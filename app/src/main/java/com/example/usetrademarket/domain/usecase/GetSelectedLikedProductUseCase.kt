package com.example.usetrademarket.domain.usecase

import com.example.usetrademarket.data.db.entity.ProductLikeEntity
import com.example.usetrademarket.domain.repository.UseTradeRepository

class GetSelectedLikedProductUseCase(
    private val useTradeRepository: UseTradeRepository
) {

    suspend fun execute(id : Long) : ProductLikeEntity? {
        return useTradeRepository.getSelectedProductListItemEntity(id)
    }
}