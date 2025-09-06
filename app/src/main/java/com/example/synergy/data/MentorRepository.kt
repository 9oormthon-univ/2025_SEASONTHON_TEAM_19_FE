package com.example.synergy.data

import com.example.synergy.ui.mentorlist.MentorCategoryDto
import com.example.synergy.ui.mentorlist.MentorPageResponse

class MentorRepository(
    private val api: MentorApi = RetrofitClient.mentorApi
) {

    // 카테고리 리스트 보기
    suspend fun getCategories(): List<MentorCategoryDto> = api.getCategories()

}