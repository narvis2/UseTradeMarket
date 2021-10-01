package com.example.usetrademarket.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.usetrademarket.data.db.dao.ProductLikeDao
import com.example.usetrademarket.data.db.entity.ProductLikeEntity

@Database(
    entities = [ProductLikeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UseTradeDatabase : RoomDatabase() {

    abstract fun productLikeDao() : ProductLikeDao

}