package com.example.usetrademarket.presentation.di

import android.app.Application
import android.content.Context
import com.example.usetrademarket.presentation.di.*
import com.example.usetrademarket.presentation.module.*
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class UseTradeMarketApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this

        startKoin {
            androidLogger(
                if (BuildConfig.DEBUG) {
                    Level.DEBUG
                } else {
                    Level.NONE
                }
            )
            androidContext(this@UseTradeMarketApp)
            modules(
                appModule,
                apiModule,
                viewModelModule,
                repositoryModule,
                useCaseModule,
                localDataModule,
                remoteDataModule
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        appContext = null
    }

    companion object {
        var appContext : Context? = null
            private set
    }
}