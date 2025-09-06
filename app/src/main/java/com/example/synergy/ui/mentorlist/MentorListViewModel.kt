package com.example.synergy.ui.mentorlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synergy.data.MentorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MentorListUiState(
    val categories: List<MentorCategoryDto> = emptyList(),
    val selectedTabIndex: Int = 0,   // 0 = "전체"
    val isLoading: Boolean = false,
    val error: String? = null,
    val mentors: List<MentorUserDto> = emptyList()
)

class MentorListViewModel(
    private val repo: MentorRepository = MentorRepository()
) : ViewModel() {

    private val _ui = MutableStateFlow(MentorListUiState())
    val ui: StateFlow<MentorListUiState> = _ui

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _ui.update { it.copy(isLoading = true, error = null) }
            try {
                val list = repo.getCategories()
                _ui.update { it.copy(categories = list, isLoading = false) }
            } catch (e: Exception) {
                _ui.update { it.copy(isLoading = false, error = e.message ?: "카테고리 로드 실패") }
            }
        }
    }

    fun onTabSelected(index: Int) {
        _ui.update { it.copy(selectedTabIndex = index) }
        // TODO: 선택한 탭(카테고리)에 맞게 멘토 리스트 API 호출 붙이기
        // val code = selectedCategoryCodeOrNull()
        // loadMentors(category = code)
    }

    fun selectedCategoryCodeOrNull(): String? {
        val i = _ui.value.selectedTabIndex
        return if (i == 0) null else _ui.value.categories.getOrNull(i - 1)?.code
    }
}