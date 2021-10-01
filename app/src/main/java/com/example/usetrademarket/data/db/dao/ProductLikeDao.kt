package com.example.usetrademarket.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.usetrademarket.data.db.entity.ProductLikeEntity
import com.example.usetrademarket.data.model.response.ProductListItemResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductLikeDao {

    @Query("SELECT * FROM ProductLikeEntity")
    fun getAll(): Flow<List<ProductLikeEntity>>

    @Query("SELECT * FROM ProductLikeEntity WHERE id=:id")
    suspend fun getSelectedProductListItemEntity(id: Long) : ProductLikeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(productLikeEntity: ProductLikeEntity)

    @Query("DELETE FROM ProductLikeEntity WHERE id=:id")
    suspend fun deleteSelectedProductListItemEntity(id: Long)

    @Query("DELETE FROM ProductLikeEntity")
    suspend fun deleteAll()
}