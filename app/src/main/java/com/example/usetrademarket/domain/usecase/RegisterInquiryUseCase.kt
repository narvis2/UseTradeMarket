package com.example.usetrademarket.domain.usecase

import com.example.usetrademarket.data.model.request.InquiryRequest
import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.domain.repository.UseTradeRepository
import retrofit2.Response

class RegisterInquiryUseCase(
    private val useTradeRepository: UseTradeRepository
) {

    suspend fun execute(inquiryRequest: InquiryRequest) : ApiResponse<Response<Void>> {
        return useTradeRepository.registerInquiry(inquiryRequest)
    }

}