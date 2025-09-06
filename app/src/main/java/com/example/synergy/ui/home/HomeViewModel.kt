package com.example.synergy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synergy.BuildConfig
import com.example.synergy.data.repository.MentorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MentorRepository = MentorRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadFavoriteMentors()
        loadRecommendedMentors()
        loadMyMentoringList()
        loadMyLectures()
        loadRecentMentors()
    }

    private fun loadFavoriteMentors() {
        viewModelScope.launch {
            runCatching { repository.getFavoriteMentors() }
                .onSuccess { _uiState.value = _uiState.value.copy(favoriteMentors = it.content) }
                .onFailure { }
        }
    }

    private fun loadRecommendedMentors() {
        viewModelScope.launch {
            runCatching { repository.getRecommendMentors() }
                .onSuccess { _uiState.value = _uiState.value.copy(recommendedMentors = it.content) }
                .onFailure { }
        }
    }

    private fun loadMyMentoringList() {
        if (BuildConfig.DEBUG)
            _uiState.value = _uiState.value.copy(myMentoringList = listOf(Pair("Leo    D-3", "2025.09.10 19:00 예약"), Pair("Nora    D-5", "2025.09.12 17:00 예약"), Pair("Owen    D-7", "2025.09.14 19:00 예약")))
        else {

        }
    }

    private fun loadMyLectures() {
        if (BuildConfig.DEBUG)
            _uiState.value = _uiState.value.copy(myLectures = listOf(Pair("AI 입문    D-13", "2025.09.20 14:00 예약"), Pair("AI 실전    D-20", "2025.09.27 14:00 예약")))
        else {

        }
    }

    fun loadRecentMentors() {
        if (BuildConfig.DEBUG)
            _uiState.value = _uiState.value.copy(recentMentors = listOf("Park", "Nora", "James"))
        else {

        }
    }
}