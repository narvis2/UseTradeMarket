package com.example.usetrademarket.domain.usecase

import com.example.usetrademarket.data.model.request.SigninRequest
import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.data.model.response.SigninResponse
import com.example.usetrademarket.domain.repository.UseTradeRepository

class SigninUseCase(
    private val userTradeRepository: UseTradeRepository
) {

    suspend fun execute(signinRequest: SigninRequest) : ApiResponse<SigninResponse> {
        return userTradeRepository.signin(signinRequest)
    }
}