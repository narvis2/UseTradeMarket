package com.example.usetrademarket.presentation.module

import com.example.usetrademarket.data.repository.GalleryPhotoRepositoryImpl
import com.example.usetrademarket.data.repository.UseTradeRepositoryImpl
import com.example.usetrademarket.domain.repository.GalleryPhotoRepository
import com.example.usetrademarket.domain.repository.UseTradeRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<UseTradeRepository> { UseTradeRepositoryImpl( get(), get()) }
    single<GalleryPhotoRepository> { GalleryPhotoRepositoryImpl(androidApplication()) }
}