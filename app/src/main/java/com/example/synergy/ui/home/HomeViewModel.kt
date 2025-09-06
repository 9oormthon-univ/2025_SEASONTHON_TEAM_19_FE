package com.example.synergy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synergy.BuildConfig
import com.example.synergy.data.model.Mentor
import com.example.synergy.data.repository.MentorRepository
import com.example.synergy.util.DummyData
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
            _uiState.value = _uiState.value.copy(myMentoringList = listOf("a", "b", "c"))
        else {

        }
    }

    private fun loadMyLectures() {
        if (BuildConfig.DEBUG)
            _uiState.value = _uiState.value.copy(myLectures = listOf("d", "e", "f"))
        else {

        }
    }

    fun loadRecentMentors() {
        if (BuildConfig.DEBUG)
            _uiState.value = _uiState.value.copy(recentMentors = listOf("g", "h", "i"))
        else {

        }
    }
}