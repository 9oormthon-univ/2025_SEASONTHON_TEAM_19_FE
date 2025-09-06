package com.example.synergy.data

import com.example.synergy.ui.mentorlist.MentorCategoryDto
import com.example.synergy.ui.mentorlist.MentorPageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MentorApi {

    // 카테고리 리스트 보기
    @GET("api/mentors/categories")
    suspend fun getCategories(): List<MentorCategoryDto>

}