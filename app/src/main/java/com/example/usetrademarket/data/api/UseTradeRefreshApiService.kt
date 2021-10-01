package com.example.usetrademarket.data.api

import com.example.usetrademarket.data.model.response.ApiResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface UseTradeRefreshApiService {

    @POST("/api/v1/refresh_token")
    suspend fun refreshToken(
        @Query("grant_type") grantType : String = "refresh_token"
    ) : ApiResponse<String>

}