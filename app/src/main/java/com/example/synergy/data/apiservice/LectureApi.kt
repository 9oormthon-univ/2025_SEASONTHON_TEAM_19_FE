package com.example.synergy.data.apiservice

import com.example.synergy.data.model.PageResponse
import retrofit2.http.GET

interface LectureApi {

    @GET("/api/lectures")
    suspend fun getLectures(): PageResponse
}