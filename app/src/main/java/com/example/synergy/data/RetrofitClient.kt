package com.example.synergy.data

import com.example.synergy.data.apiservice.AuthApi
import com.example.synergy.data.apiservice.MentorApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

object RetrofitClient {
    const val BASE_URL = "http://3.39.158.137:8080/"

    private val logging by lazy {
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }
    private val client by lazy {
        OkHttpClient.Builder().addInterceptor(logging).build()
    }
    private val gson by lazy {
        GsonBuilder()
            .setLenient()
            .create()
    }
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val authApi: AuthApi by lazy { retrofit.create(AuthApi::class.java) }
    val mentorApi: MentorApi by lazy { retrofit.create(MentorApi::class.java) }
}