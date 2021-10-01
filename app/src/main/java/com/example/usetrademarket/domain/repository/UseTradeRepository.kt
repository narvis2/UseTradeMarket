package com.example.usetrademarket.domain.repository

import com.example.usetrademarket.data.db.entity.ProductLikeEntity
import com.example.usetrademarket.data.model.request.InquiryRequest
import com.example.usetrademarket.data.model.request.ProductRegistrationRequest
import com.example.usetrademarket.data.model.request.SigninRequest
import com.example.usetrademarket.data.model.request.SignupRequest
import com.example.usetrademarket.data.model.response.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response

interface UseTradeRepository {

    suspend fun getIntro() : String

    suspend fun signup(signupRequest: SignupRequest) : ApiResponse<Void>

    suspend fun signin(signinRequest: SigninRequest) : ApiResponse<SigninResponse>

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
        direction: String, // prev, next
        keyword: String?
    ) : ApiResponse<List<ProductListItemResponse>>

    suspend fun getProduct(
        productId: Long
    ) : ApiResponse<ProductResponse>

    suspend fun registerInquiry(
        inquiryRequest: InquiryRequest
    ) : ApiResponse<Response<Void>>

    fun getAllUserLikedProduct() : Flow<List<ProductLikeEntity>>

    suspend fun getSelectedProductListItemEntity(id: Long) : ProductLikeEntity?

    suspend fun insert(productLikeEntity: ProductLikeEntity)

    suspend fun deleteSelectedProductListItemEntity(id: Long)

    suspend fun deleteAll()
}