package com.example.usetrademarket.presentation.module

import com.example.usetrademarket.data.repository.daatasource.UseTradeRemoteDataSource
import com.example.usetrademarket.data.repository.daatasourceimpl.UseTradeRemoteDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val remoteDataModule : Module = module {
    single<UseTradeRemoteDataSource> { UseTradeRemoteDataSourceImpl(get()) }
}