package com.example.usetrademarket.domain.usecase

import com.example.usetrademarket.domain.repository.UseTradeRepository

class DeleteAllLikedProductUseCase(
    private val useTradeRepository: UseTradeRepository
) {

    suspend fun execute() {
        useTradeRepository.deleteAll()
    }
}