package com.example.synergy.ui.mentorlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synergy.MainActivity.Companion.categories
import com.example.synergy.data.repository.MentorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MentorListViewModel(
    private val repo: MentorRepository = MentorRepository()
) : ViewModel() {

    private val _ui = MutableStateFlow(MentorListUiState())
    val ui: StateFlow<MentorListUiState> = _ui

    init {
        loadCategories()
    }

    // 카테고리 리스트
    fun loadCategories() {
        viewModelScope.launch {
            _ui.update { it.copy(isLoading = true, error = null) }
            try {
                val list = repo.getCategories()
                _ui.update { it.copy(categories = list, isLoading = false) }
                categories = list
            } catch (e: Exception) {
                _ui.update { it.copy(isLoading = false, error = e.message ?: "카테고리 로드 실패") }
            }
        }
    }

    fun onTabSelected(index: Int) {
        _ui.update { it.copy(selectedTabIndex = index) }
        refreshMentors()
    }

    fun refreshMentors() {
        _ui.update { it.copy(mentors = emptyList(), page = 0, isEnd = false, error = null) }
        loadNextPage()
    }

    fun loadNextPage() {
        val s = _ui.value
        if (s.isLoading || s.isEnd) return

        viewModelScope.launch {
            _ui.update { it.copy(isLoading = true, error = null) }
            try {
                val res = selectedCategoryCodeOrNull()?.let { code ->
                    repo.getMentorsByCategory(code, page = s.page, size = s.pageSize)
                } ?: repo.getMentors(page = s.page, size = s.pageSize)

                _ui.update {
                    it.copy(
                        mentors = it.mentors + res.content,
                        page = s.page + 1,
                        isEnd = res.last,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _ui.update { it.copy(isLoading = false, error = e.message ?: "멘토 목록 로드 실패") }
            }
        }
    }

    fun selectedCategoryCodeOrNull(): String? {
        val i = _ui.value.selectedTabIndex
        return if (i == 0) null else _ui.value.categories.getOrNull(i - 1)?.code
    }
}