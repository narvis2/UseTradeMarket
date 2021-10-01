package com.example.usetrademarket.domain.usecase

import com.example.usetrademarket.data.model.request.SignupRequest
import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.domain.repository.UseTradeRepository

class SignupUseCase(
    private val useTradeRepository: UseTradeRepository
) {
    suspend fun execute(signupRequest: SignupRequest) : ApiResponse<Void> {
        return useTradeRepository.signup(signupRequest)
    }
}