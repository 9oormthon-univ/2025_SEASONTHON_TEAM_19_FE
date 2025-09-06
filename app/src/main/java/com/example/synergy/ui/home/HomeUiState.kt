package com.example.synergy.ui.home

import com.example.synergy.data.model.Mentor

data class HomeUiState(
    val recommendedMentors: List<Mentor> = emptyList(),
    val favoriteMentors: List<Mentor> = emptyList(),
    val myMentoringList: List<String> = emptyList(),
    val myLectures: List<String> = emptyList(),
    val recentMentors: List<String> = emptyList(),
)