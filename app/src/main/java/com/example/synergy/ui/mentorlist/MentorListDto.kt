package com.example.synergy.ui.mentorlist

data class MentorCategoryDto(
    val code: String,
    val name: String
)

data class MentorUserDto(
    val id: Int,
    val username: String,
    val email: String,
    val mentor: Boolean,
    val categories: List<MentorCategoryDto>,
    val introduction: String?
)

data class MentorPageResponse(
    val content: List<MentorUserDto>,
    val last: Boolean,
    val number: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int,
    val first: Boolean,
    val numberOfElements: Int,
    val empty: Boolean
)