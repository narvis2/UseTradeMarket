package com.example.usetrademarket.data.api.interceptor

import android.content.Context
import android.content.Intent
import com.example.usetrademarket.data.preference.AppPreferenceManager
import com.example.usetrademarket.presentation.view.signin.SigninActivity
import okhttp3.Interceptor
import okhttp3.Response

class TokenRefreshInterceptor(
    private val context : Context,
    private val appPreferenceManager: AppPreferenceManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        println("-------------------------------")
        println("토큰 갱신 요청")
        println("-------------------------------")

        val original = chain.request()
        val request = original.newBuilder().apply {
            appPreferenceManager.getRefreshToken()?.let { header("Authorization", it) }
            method(original.method, original.body)
        }.build()

        val response = chain.proceed(request)

        if (response.code == 401) {
            context.run {
                val intent = Intent(this, SigninActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
            }
        }

        return response
    }
}