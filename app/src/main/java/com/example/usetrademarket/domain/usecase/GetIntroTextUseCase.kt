package com.example.usetrademarket.domain.usecase

import com.example.usetrademarket.domain.repository.UseTradeRepository

class GetIntroTextUseCase(
    private val useTradeRepository: UseTradeRepository
) {
    suspend fun execute() : String {
        return useTradeRepository.getIntro()
    }
}