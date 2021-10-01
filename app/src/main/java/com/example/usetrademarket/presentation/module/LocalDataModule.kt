package com.example.usetrademarket.presentation.module

import androidx.room.Room
import com.example.usetrademarket.data.db.UseTradeDatabase
import com.example.usetrademarket.data.db.dao.ProductLikeDao
import com.example.usetrademarket.data.preference.AppPreferenceManager
import com.example.usetrademarket.data.repository.daatasource.UseTradeLocalDataSource
import com.example.usetrademarket.data.repository.daatasourceimpl.UseTradeLocalDataSourceImpl
import com.example.usetrademarket.presentation.utils.DB_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

val localDataModule : Module = module {

    single<AppPreferenceManager> { AppPreferenceManager(androidApplication()) }

    single<ProductLikeDao> { get<UseTradeDatabase>().productLikeDao() }

    single<UseTradeLocalDataSource> { UseTradeLocalDataSourceImpl(get()) }

    single<UseTradeDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            UseTradeDatabase::class.java,
            DB_NAME
        ).build()
    }
}