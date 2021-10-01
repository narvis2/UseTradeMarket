package com.example.usetrademarket.data.repository.daatasourceimpl

import com.example.usetrademarket.data.api.UseTradeApiService
import com.example.usetrademarket.data.model.request.InquiryRequest
import com.example.usetrademarket.data.model.request.ProductRegistrationRequest
import com.example.usetrademarket.data.model.request.SigninRequest
import com.example.usetrademarket.data.model.request.SignupRequest
import com.example.usetrademarket.data.model.response.*
import com.example.usetrademarket.data.repository.daatasource.UseTradeRemoteDataSource
import okhttp3.MultipartBody
import retrofit2.Response

class UseTradeRemoteDataSourceImpl(
    private val useTradeApiService: UseTradeApiService
) : UseTradeRemoteDataSource {

    override suspend fun getIntro(): ApiResponse<String> {
        return useTradeApiService.getIntro()
    }

    override suspend fun signup(signupRequest: SignupRequest): ApiResponse<Void> {
        return useTradeApiService.signup(signupRequest)
    }

    override suspend fun signin(signinRequest: SigninRequest): ApiResponse<SigninResponse> {
        return useTradeApiService.signin(signinRequest)
    }

    override suspend fun uploadProductImages(images: MultipartBody.Part): ApiResponse<ProductImageUploadResponse> {
        return useTradeApiService.uploadProductImages(images)
    }

    override suspend fun registerProduct(request: ProductRegistrationRequest): ApiResponse<Response<Void>> {
        return useTradeApiService.registerProduct(request)
    }

    override suspend fun getProducts(
        productId: Long,
        categoryId: Int?,
        direction: String,
    ): ApiResponse<List<ProductListItemResponse>> {
        return useTradeApiService.getProducts(productId, categoryId, direction)
    }

    override suspend fun getProductsByKeyword(
        productId: Long,
        categoryId: Int?,
        direction: String,
        keyword: String?,
    ) : ApiResponse<List<ProductListItemResponse>> {
        return useTradeApiService.getProducts(productId, categoryId, direction, keyword)
    }

    override suspend fun getProduct(productId: Long): ApiResponse<ProductResponse> {
        return useTradeApiService.getProduct(productId)
    }

    override suspend fun registerInquiry(inquiryRequest: InquiryRequest): ApiResponse<Response<Void>> {
        return useTradeApiService.registerInquiry(inquiryRequest)
    }
}