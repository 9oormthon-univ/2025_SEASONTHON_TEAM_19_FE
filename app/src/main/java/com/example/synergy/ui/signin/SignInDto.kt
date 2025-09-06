package com.example.synergy.ui.signin

data class SignInRequest(
    val username: String,
    val password: String
)

data class SignInResponse(
    val tokenType: String,
    val accessToken: String,
    val expiresIn: Int
)