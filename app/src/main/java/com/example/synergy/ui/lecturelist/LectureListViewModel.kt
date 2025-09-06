package com.example.synergy.ui.lecturelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synergy.data.model.PageResponse
import com.example.synergy.data.repository.LectureRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LectureListViewModel(
    private val repository: LectureRepository = LectureRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(PageResponse())
    val uiState: StateFlow<PageResponse> = _uiState

    init {
        loadLectures()
    }

    private fun loadLectures() {
        viewModelScope.launch {
            runCatching { repository.getLectures() }
                .onSuccess { _uiState.value = _uiState.value.copy(content = it.content) }
                .onFailure { }
        }
    }
}