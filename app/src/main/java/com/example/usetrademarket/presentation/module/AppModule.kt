package com.example.usetrademarket.presentation.module

import com.example.usetrademarket.data.api.fcm.NotificationId
import com.example.usetrademarket.data.api.fcm.UseTradeMessagingService
import com.example.usetrademarket.presentation.utils.IO
import com.example.usetrademarket.presentation.utils.MAIN
import com.example.usetrademarket.presentation.utils.MenuChangeEventBus
import com.example.usetrademarket.presentation.utils.provider.ResourcesProvider
import com.example.usetrademarket.presentation.utils.provider.ResourcesProviderImpl
import com.example.usetrademarket.presentation.view.registration.ProductImageUploader
import com.example.usetrademarket.presentation.view.signin.SigninActivity
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule : Module = module {
    single(named(IO)) { Dispatchers.IO }
    single(named(MAIN)) { Dispatchers.Main }
    single<MenuChangeEventBus> { MenuChangeEventBus() }
    single<ResourcesProvider> { ResourcesProviderImpl(androidApplication()) }
    single<ProductImageUploader> { ProductImageUploader(get()) }
    single<NotificationId> { NotificationId(get()) }
}