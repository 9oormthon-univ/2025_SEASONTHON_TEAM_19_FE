package com.example.synergy.ui.home

import androidx.lifecycle.ViewModel
import com.example.synergy.BuildConfig
import com.example.synergy.data.model.Mentor
import com.example.synergy.util.DummyData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadFavoriteMentors()
        loadRecommendedMentors()
        loadMyMentoringList()
        loadMyLectures()
        loadRecentMentors()
    }

    fun loadFavoriteMentors() {
        if (BuildConfig.DEBUG)
            _uiState.value = _uiState.value.copy(favoriteMentors = DummyData.dummyMentors)
        else {

        }
    }

    fun loadRecommendedMentors() {
        if (BuildConfig.DEBUG)
            _uiState.value = _uiState.value.copy(recommendedMentors = DummyData.dummyMentors)
        else {

        }
    }

    fun loadMyMentoringList() {
        if (BuildConfig.DEBUG)
            _uiState.value = _uiState.value.copy(myMentoringList = listOf("a", "b", "c"))
        else {

        }
    }

    fun loadMyLectures() {
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