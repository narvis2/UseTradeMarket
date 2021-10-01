package com.example.usetrademarket.data.mapper

import com.example.usetrademarket.data.db.entity.ProductLikeEntity
import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.data.model.response.ProductListItemResponse
import com.example.usetrademarket.data.model.response.ProductResponse
import com.example.usetrademarket.domain.model.ProductLikeModel
import com.example.usetrademarket.domain.model.ProductModel

fun ApiResponse<List<ProductListItemResponse>>.toListProducts() : List<ProductListItemResponse> =
    if (success && data != null) {
        data.map {
            ProductListItemResponse(
                id = it.id,
                name = it.name,
                description = it.description,
                price = it.price,
                status = it.status,
                sellerId = it.sellerId,
                imagePaths = it.imagePaths,
                sellerName = it.sellerName
            )
        }
    } else {
        emptyList()
    }

fun List<ProductResponse>.toProductModelList() = map {
    ProductModel(
        id = it.id,
        name = it.name,
        description = it.description,
        price = it.price,
        status = it.status,
        sellerId = it.sellerId,
        imagePaths = it.imagePaths
    )
}

fun ProductResponse.toProductModels() : List<ProductModel> = arrayListOf(
    ProductModel(
        id = id,
        name = name,
        description = description,
        price = price,
        status = status,
        sellerId = sellerId,
        imagePaths = imagePaths
    )
)

fun ApiResponse<List<ProductListItemResponse>>.toProductLikeEntity() =
    if (success && data != null) {
        data.map {
            ProductLikeEntity(
                id = it.id,
                name = it.name,
                description = it.description,
                price = it.price,
                status = it.status,
                sellerId = it.sellerId,
                imagePaths = it.imagePaths.first()
            )
        }
    } else {
        emptyList()
    }

fun ApiResponse<ProductResponse>.toProductLikeEntity() =
    data?.let {
        ProductLikeEntity(
            it.id,
            it.name,
            it.description,
            it.price,
            it.status,
            it.sellerId,
            it.imagePaths.first()
        )
    }

fun ProductResponse.toProductEntity() =
    ProductLikeEntity(
        id,
        name,
        description,
        price,
        status,
        sellerId,
        imagePaths.first()
    )

fun ProductListItemResponse.toProductLikeEntity(isLiked: Boolean) =
    ProductLikeEntity(
        id = id,
        name = name,
        description = description,
        price = price,
        status = status,
        sellerId = sellerId,
        imagePaths = imagePaths.first()
    )

fun List<ProductLikeEntity>.toProductLikeModel() = map {
    ProductLikeModel(
        id = it.id,
        name = it.name,
        description = it.description,
        price = it.price,
        status = it.status,
        sellerId = it.sellerId,
        imagePaths = it.imagePaths
    )
}

fun ProductLikeModel.toProductLikeEntity() =
    ProductLikeEntity(
        id = id,
        name = name,
        description = description,
        price = price,
        status = status,
        sellerId = sellerId,
        imagePaths = imagePaths
    )

