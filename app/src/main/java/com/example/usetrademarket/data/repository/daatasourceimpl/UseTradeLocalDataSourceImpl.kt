package com.example.usetrademarket.data.repository.daatasourceimpl

import com.example.usetrademarket.data.db.dao.ProductLikeDao
import com.example.usetrademarket.data.db.entity.ProductLikeEntity
import com.example.usetrademarket.data.repository.daatasource.UseTradeLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn

class UseTradeLocalDataSourceImpl(
    private val productLikeDao: ProductLikeDao
) : UseTradeLocalDataSource {
    override fun getAll(): Flow<List<ProductLikeEntity>> =
        productLikeDao.getAll().distinctUntilChanged()
            .flowOn(Dispatchers.IO)

    override suspend fun getSelectedProductListItemEntity(id: Long): ProductLikeEntity? {
        return productLikeDao.getSelectedProductListItemEntity(id)
    }

    override suspend fun insert(productLikeEntity: ProductLikeEntity) {
        productLikeDao.insert(productLikeEntity)
    }

    override suspend fun deleteSelectedProductListItemEntity(id: Long) {
        productLikeDao.deleteSelectedProductListItemEntity(id)
    }

    override suspend fun deleteAll() {
        productLikeDao.deleteAll()
    }
}