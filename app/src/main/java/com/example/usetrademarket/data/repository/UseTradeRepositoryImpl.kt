package com.example.usetrademarket.data.repository

import android.util.Log
import com.example.usetrademarket.data.db.entity.ProductLikeEntity
import com.example.usetrademarket.data.model.request.InquiryRequest
import com.example.usetrademarket.data.model.request.ProductRegistrationRequest
import com.example.usetrademarket.data.model.request.SigninRequest
import com.example.usetrademarket.data.model.request.SignupRequest
import com.example.usetrademarket.data.model.response.*
import com.example.usetrademarket.data.repository.daatasource.UseTradeLocalDataSource
import com.example.usetrademarket.data.repository.daatasource.UseTradeRemoteDataSource
import com.example.usetrademarket.domain.repository.UseTradeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import retrofit2.Response

class UseTradeRepositoryImpl(
    private val userTradeRemoteDataSource: UseTradeRemoteDataSource,
    private val userTradeLocalDataSource: UseTradeLocalDataSource
) : UseTradeRepository {
    override suspend fun getIntro(): String {
        return try {
            userTradeRemoteDataSource.getIntro().data?.let {
                it
            } ?: kotlin.run {
                userTradeRemoteDataSource.getIntro().message.toString()
            }
        }catch (e: Exception) {
            Log.e("UseTradeRepositoryImpl", e.message.toString())
            e.message.toString()
        }
    }

    override suspend fun signup(signupRequest: SignupRequest) : ApiResponse<Void>  {
        return userTradeRemoteDataSource.signup(signupRequest)
    }

    override suspend fun signin(signinRequest: SigninRequest): ApiResponse<SigninResponse> {
        return userTradeRemoteDataSource.signin(signinRequest)
    }

    override suspend fun uploadProductImages(images: MultipartBody.Part): ApiResponse<ProductImageUploadResponse> {
        return userTradeRemoteDataSource.uploadProductImages(images)
    }

    override suspend fun registerProduct(request: ProductRegistrationRequest): ApiResponse<Response<Void>> {
        return userTradeRemoteDataSource.registerProduct(request)
    }

    override suspend fun getProducts(
        productId: Long,
        categoryId: Int?,
        direction: String,
    ): ApiResponse<List<ProductListItemResponse>> {
        return userTradeRemoteDataSource.getProducts(productId, categoryId, direction)
    }

    override suspend fun getProductsByKeyword(
        productId: Long,
        categoryId: Int?,
        direction: String,
        keyword: String?,
    ): ApiResponse<List<ProductListItemResponse>> {
        return userTradeRemoteDataSource.getProductsByKeyword(productId, categoryId, direction, keyword)
    }

    override suspend fun getProduct(productId: Long): ApiResponse<ProductResponse> {
        return userTradeRemoteDataSource.getProduct(productId)
    }

    override suspend fun registerInquiry(inquiryRequest: InquiryRequest): ApiResponse<Response<Void>> {
        return userTradeRemoteDataSource.registerInquiry(inquiryRequest)
    }

    override fun getAllUserLikedProduct(): Flow<List<ProductLikeEntity>> {
        return userTradeLocalDataSource.getAll().distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    override suspend fun getSelectedProductListItemEntity(id: Long): ProductLikeEntity? {
        return userTradeLocalDataSource.getSelectedProductListItemEntity(id)
    }

    override suspend fun insert(productLikeEntity: ProductLikeEntity) {
        userTradeLocalDataSource.insert(productLikeEntity)
    }

    override suspend fun deleteSelectedProductListItemEntity(id: Long) {
        userTradeLocalDataSource.deleteSelectedProductListItemEntity(id)
    }

    override suspend fun deleteAll() {
        userTradeLocalDataSource.deleteAll()
    }
}