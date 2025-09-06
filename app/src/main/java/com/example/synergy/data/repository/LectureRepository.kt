package com.example.synergy.data.repository

import com.example.synergy.data.RetrofitClient
import com.example.synergy.data.apiservice.LectureApi
import com.example.synergy.data.model.PageResponse

class LectureRepository(private val api: LectureApi = RetrofitClient.lectureApi) {

    suspend fun getLectures(): PageResponse {
        return api.getLectures()
    }
}