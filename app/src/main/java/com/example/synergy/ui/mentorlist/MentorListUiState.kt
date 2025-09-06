package com.example.synergy.ui.mentorlist

import com.example.synergy.data.model.Category
import com.example.synergy.data.model.Mentor

data class MentorListUiState(
    val categories: List<Category> = emptyList(),
    val selectedTabIndex: Int = 0,   // 0 = "전체"
    val isLoading: Boolean = false,
    val error: String? = null,
    val mentors: List<Mentor> = emptyList(),
    val page: Int = 0,
    val pageSize: Int = 10,
    val isEnd: Boolean = false,
)