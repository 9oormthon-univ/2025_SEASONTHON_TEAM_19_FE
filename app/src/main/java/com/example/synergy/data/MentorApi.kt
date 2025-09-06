package com.example.synergy.data

import com.example.synergy.ui.mentorlist.MentorCategoryDto
import com.example.synergy.ui.mentorlist.MentorPageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MentorApi {

    // 카테고리 리스트 보기
    @GET("api/mentors/categories")
    suspend fun getCategories(): List<MentorCategoryDto>

    // 전체 멘토 리스트 보기
    @GET("api/mentors")
    suspend fun getMentors(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String = "id,asc"
    ): MentorPageResponse

    // 선택한 카테고리에 해당하는 멘토 리스트 보기
    @GET("api/mentors/by-category")
    suspend fun getMentorsByCategory(
        @Query("categoryCode") categoryCode: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String = "id,asc"
    ): MentorPageResponse
}