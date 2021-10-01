package com.example.usetrademarket.data.repository.daatasource

import com.example.usetrademarket.data.model.request.InquiryRequest
import com.example.usetrademarket.data.model.request.ProductRegistrationRequest
import com.example.usetrademarket.data.model.request.SigninRequest
import com.example.usetrademarket.data.model.request.SignupRequest
import com.example.usetrademarket.data.model.response.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UseTradeRemoteDataSource {

    suspend fun getIntro(): ApiResponse<String>

    suspend fun signup(signupRequest: SignupRequest): ApiResponse<Void>

    suspend fun signin(signinRequest: SigninRequest): ApiResponse<SigninResponse>

    suspend fun uploadProductImages(
        images: MultipartBody.Part
    ): ApiResponse<ProductImageUploadResponse>

    suspend fun registerProduct(
        request: ProductRegistrationRequest
    ) : ApiResponse<Response<Void>>

    suspend fun getProducts(
        productId : Long,
        categoryId : Int?,
        direction: String // prev, next
    ) : ApiResponse<List<ProductListItemResponse>>

    suspend fun getProductsByKeyword(
        productId : Long,
        categoryId : Int?,
        direction: String,
        keyword: String?
    ) : ApiResponse<List<ProductListItemResponse>>

    suspend fun getProduct(productId : Long) : ApiResponse<ProductResponse>

    suspend fun registerInquiry(inquiryRequest: InquiryRequest) : ApiResponse<Response<Void>>
}