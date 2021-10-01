package com.example.usetrademarket.domain.usecase

import com.example.usetrademarket.domain.repository.UseTradeRepository

class DeleteSelectedLikedProductUseCase(
    private val useTradeRepository: UseTradeRepository
) {

    suspend fun execute(id: Long) {
        useTradeRepository.deleteSelectedProductListItemEntity(id)
    }
}