package com.example.synergy.data

import com.example.synergy.ui.signin.SignInRequest
import com.example.synergy.ui.signin.SignInResponse
import com.example.synergy.ui.signup.SignUpRequest
import com.example.synergy.ui.signup.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    // 회원가입
    @POST("api/auth/signup")
    suspend fun signUp(@Body body: SignUpRequest): Response<SignUpResponse>

    // 로그인
    @POST("api/auth/login")
    suspend fun signIn(@Body body: SignInRequest): Response<SignInResponse>

}