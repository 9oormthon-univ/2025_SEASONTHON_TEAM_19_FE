package com.example.synergy.ui.mentordetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synergy.data.model.MentorDetail
import com.example.synergy.data.repository.MentorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface MentorDetailUiState {
    object Loading : MentorDetailUiState
    data class Success(val data: MentorDetail) : MentorDetailUiState
    data class Error(val message: String) : MentorDetailUiState
}

class MentorDetailViewModel(
    private val repository: MentorRepository = MentorRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<MentorDetailUiState>(MentorDetailUiState.Loading)
    val uiState: StateFlow<MentorDetailUiState> = _uiState

    fun load(id: Int) {
        // 같은 id로 중복 로드 방지
        if (_uiState.value is MentorDetailUiState.Success &&
            (uiState.value as MentorDetailUiState.Success).data.id == id
        ) return

        _uiState.value = MentorDetailUiState.Loading
        viewModelScope.launch {
            runCatching { repository.getMentorDetail(id) }
                .onSuccess { _uiState.value = MentorDetailUiState.Success(it) }
                .onFailure { _uiState.value = MentorDetailUiState.Error(it.message ?: "알 수 없는 오류") }
        }
    }
}