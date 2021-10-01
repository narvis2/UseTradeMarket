package com.example.usetrademarket.data.model.request

import android.util.Patterns

data class SigninRequest(
    val email: String?,
    val password: String?
) {

    fun isValidEmail() =
        email.isNullOrBlank()
                || !Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isNotValidPassword() =
        password.isNullOrBlank() || password.length !in 8..20
}