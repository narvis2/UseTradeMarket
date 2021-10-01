package com.example.usetrademarket.data.model.request

import android.util.Patterns
import com.example.usetrademarket.data.preference.AppPreferenceManager
import com.example.usetrademarket.presentation.di.UseTradeMarketApp

data class SignupRequest(
    val email: String?,
    val password: String?,
    val name: String?,
    val fcmToken: String? = UseTradeMarketApp.appContext?.let {
        AppPreferenceManager(it).getFcmToken()
    }
) {
    fun isNotValidEmail() : Boolean =
        email.isNullOrBlank()
                || !Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isNotValidPassword() : Boolean =
        password.isNullOrBlank() || password.length !in 8..20

    fun isNotValidName() : Boolean=
        name.isNullOrBlank() || name.length !in 2..20
}
