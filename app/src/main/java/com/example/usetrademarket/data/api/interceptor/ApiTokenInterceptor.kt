package com.example.usetrademarket.data.api.interceptor

import com.example.usetrademarket.data.preference.AppPreferenceManager
import okhttp3.Interceptor
import okhttp3.Response

class ApiTokenInterceptor(
    private val appPreferenceManager: AppPreferenceManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        println("--------------------------------")
        println("API 요청")
        println("--------------------------------")
        val original = chain.request()
        val request = original.newBuilder().apply {
            appPreferenceManager.getToken()?.let { header("Authorization", it) }
            method(original.method, original.body)
        }.build()

        return chain.proceed(request)
    }
}