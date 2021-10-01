package com.example.usetrademarket.domain.usecase

import com.example.usetrademarket.data.model.request.ProductRegistrationRequest
import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.domain.repository.UseTradeRepository
import retrofit2.Response
import retrofit2.http.Body

class RegisterProductUseCase(
    private val useTradeRepository: UseTradeRepository
) {

    suspend fun execute(
        request: ProductRegistrationRequest
    ) : ApiResponse<Response<Void>> {
        return useTradeRepository.registerProduct(request)
    }
}