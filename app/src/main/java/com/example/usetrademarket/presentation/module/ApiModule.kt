package com.example.usetrademarket.presentation.module

import com.example.usetrademarket.data.api.ApiClient
import com.example.usetrademarket.data.api.interceptor.TokenAuthenticator
import com.example.usetrademarket.data.api.UseTradeApiService
import com.example.usetrademarket.data.api.UseTradeRefreshApiService
import com.example.usetrademarket.data.api.fcm.UseTradeMessagingService
import com.example.usetrademarket.data.api.interceptor.ApiTokenInterceptor
import com.example.usetrademarket.data.api.interceptor.TokenRefreshInterceptor
import com.example.usetrademarket.data.preference.AppPreferenceManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule : Module = module {

    single<UseTradeApiService> { get<Retrofit>(named(GENERATE_CLIENT)).create(UseTradeApiService::class.java) }

    single<UseTradeRefreshApiService> { get<Retrofit>(named(GENERATE_REFRESH_CLIENT)).create(UseTradeRefreshApiService::class.java) }

    single<UseTradeMessagingService> { UseTradeMessagingService() }

    single<Retrofit>(named(GENERATE_CLIENT)) {
        Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .client(get<OkHttpClient>(named(LOGGING_OKHTTP)))
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

    single<Retrofit>(named(GENERATE_REFRESH_CLIENT)) {
        Retrofit.Builder()
            .baseUrl(ApiClient.BASE_URL)
            .client(get<OkHttpClient>(named(AUTH_OKHTTP)))
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

    single<GsonConverterFactory> { GsonConverterFactory.create() }

    single<OkHttpClient>(named(LOGGING_OKHTTP)) {
        OkHttpClient.Builder()
            .apply {
                addInterceptor(get<HttpLoggingInterceptor>())
                addInterceptor(get<ApiTokenInterceptor>())
                authenticator(get<TokenAuthenticator>())
            }
            .build()
    }

    single<OkHttpClient>(named(AUTH_OKHTTP)) {
        OkHttpClient.Builder()
            .apply {
                addInterceptor(get<HttpLoggingInterceptor>())
                addInterceptor(get<TokenRefreshInterceptor>())
            }
            .build()
    }

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single<ApiTokenInterceptor> { ApiTokenInterceptor(get()) }

    single<TokenRefreshInterceptor> { TokenRefreshInterceptor(androidApplication(), get()) }

    single<TokenAuthenticator> { TokenAuthenticator(get(), get()) }
}