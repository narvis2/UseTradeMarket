package com.example.usetrademarket.data.api.interceptor

import com.example.usetrademarket.data.api.UseTradeRefreshApiService
import com.example.usetrademarket.data.model.response.ApiResponse
import com.example.usetrademarket.data.preference.AppPreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route


class TokenAuthenticator(
    private val useTradeRefreshApiService: UseTradeRefreshApiService,
    private val appPreferenceManager: AppPreferenceManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            println("토큰 갱신 필요")
            return runBlocking {
                val tokenResponse = refreshToken()

                if (tokenResponse.success) {
                    println("토큰 갱신 성공")
                    appPreferenceManager.setToken(tokenResponse.data)
                } else {
                    println("토큰 갱신 실패")
                    appPreferenceManager.removeToken()
                    appPreferenceManager.removeRefreshToken()
                }

                appPreferenceManager.getToken()?.let { token ->
                    response.request
                        .newBuilder()
                        .header("Authorization", token)
                        .build()
                }
            }
        }

        return null
    }

    private suspend fun refreshToken() =
        withContext(Dispatchers.IO) {
            try {
                useTradeRefreshApiService.refreshToken()
            }catch (e:Exception) {
                ApiResponse.error<String>("인증 실패")
            }
        }
}