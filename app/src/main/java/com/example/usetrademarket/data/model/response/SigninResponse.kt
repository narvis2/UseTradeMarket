package com.example.usetrademarket.data.model.response

data class SigninResponse(
    val token : String,
    val refreshToken: String,
    val userName: String,
    val userId: Long,
    val email: String
)