package com.example.usetrademarket.data.repository.daatasource


import com.example.usetrademarket.data.db.entity.ProductLikeEntity
import kotlinx.coroutines.flow.Flow

interface UseTradeLocalDataSource {

    fun getAll(): Flow<List<ProductLikeEntity>>

    suspend fun getSelectedProductListItemEntity(id: Long) : ProductLikeEntity?

    suspend fun insert(productLikeEntity: ProductLikeEntity)

    suspend fun deleteSelectedProductListItemEntity(id: Long)

    suspend fun deleteAll()

}