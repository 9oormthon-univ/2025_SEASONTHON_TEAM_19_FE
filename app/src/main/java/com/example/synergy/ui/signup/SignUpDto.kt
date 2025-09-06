package com.example.synergy.ui.signup

data class SignUpRequest(
    val email: String,
    val username: String,
    val password: String
)

data class SignUpResponse(
    val id: Long,
    val email: String,
    val username: String
)

data class SignUpUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)