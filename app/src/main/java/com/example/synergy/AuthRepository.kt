package com.example.synergy

import com.example.synergy.ui.signup.SignUpRequest

class AuthRepository(private val api: AuthApi) {

    // 회원가입
    suspend fun signUp(email: String, username: String, password: String): Result<Unit> {
        val res = api.signUp(SignUpRequest(email, username, password))
        return if (res.isSuccessful) {
            Result.success(Unit)
        } else {
            val msg = res.errorBody()?.string()?.ifBlank { null } ?: "회원가입에 실패했습니다. (${res.code()})"
            Result.failure(IllegalStateException(msg))
        }
    }

    // 로그인

}