package com.example.synergy.data.repository

import com.example.synergy.data.RetrofitClient
import com.example.synergy.data.apiservice.MentorApi
import com.example.synergy.data.model.Category
import com.example.synergy.data.model.MentorApplicationRequest
import com.example.synergy.data.model.MentorApplicationResponse
import com.example.synergy.data.model.MentorPageResponse

class MentorRepository(
    private val api: MentorApi = RetrofitClient.mentorApi,
) {
    // 카테고리 리스트 보기
    suspend fun getCategories(): List<Category> = api.getCategories()

    // 전체 멘토 리스트 보기
    suspend fun getMentors(page: Int, size: Int): MentorPageResponse =
        api.getMentors(page = page, size = size, sort = "id,asc")

    // 선택한 카테고리에 해당하는 멘토 리스트보기
    suspend fun getMentorsByCategory(
        categoryCode: String,
        page: Int,
        size: Int,
    ): MentorPageResponse =
        api.getMentorsByCategory(categoryCode, page, size)

    // 멘토 상세 보기
    suspend fun getMentorDetail(id: Int) = api.getMentorDetail(id)

    suspend fun getFavoriteMentors(): MentorPageResponse = api.getFavoriteMentors()

    suspend fun getRecommendMentors(): MentorPageResponse =
        api.getRecommendMentors(page = 0, size = 10, sort = "id,asc")
}