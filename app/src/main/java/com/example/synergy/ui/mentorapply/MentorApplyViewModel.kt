package com.example.synergy.ui.mentorapply

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synergy.data.model.MentorApplicationRequest
import com.example.synergy.data.model.MentorApplicationResponse
import com.example.synergy.data.repository.MentorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.example.synergy.data.model.MentorDetail

data class MentorApplyUiState(
    val name: String = "",
    val phone: String = "",
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val content: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val success: MentorApplicationResponse? = null
) {
    val isValid: Boolean
        get() = name.isNotBlank() &&
                phone.isNotBlank() &&
                date != null &&
                time != null &&
                content.isNotBlank()
}

class MentorApplyViewModel(
    private val repository: MentorRepository = MentorRepository()
) : ViewModel() {

    private val _mentor = MutableStateFlow<MentorDetail?>(null)
    val mentor: StateFlow<MentorDetail?> = _mentor

    fun loadMentor(mentorId: Int) {
        viewModelScope.launch {
            try {
                _mentor.value = repository.getMentorDetail(mentorId)
            } catch (e: Exception) {
                // 필요하면 에러 상태 처리
            }
        }
    }

    private val _ui = MutableStateFlow(MentorApplyUiState())
    val ui: StateFlow<MentorApplyUiState> = _ui

    fun onNameChange(v: String) = _ui.update { it.copy(name = v, error = null) }
    fun onPhoneChange(v: String) = _ui.update { it.copy(phone = v, error = null) }
    fun onDateChange(v: LocalDate) = _ui.update { it.copy(date = v, error = null) }
    fun onTimeChange(v: LocalTime) = _ui.update { it.copy(time = v, error = null) }
    fun onContentChange(v: String) = _ui.update { it.copy(content = v, error = null) }

    fun submit(mentorId: Int) {
        val state = _ui.value
        if (!state.isValid || state.loading) return

        val scheduledAt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            .format(state.date!!.atTime(state.time!!))

        viewModelScope.launch {
            _ui.value = state.copy(loading = true, error = null, success = null)
            try {
                val res = repository.submit(
                    mentorId,
                    MentorApplicationRequest(
                        applicantName = state.name.trim(),
                        phone = state.phone.trim(),
                        scheduledAt = scheduledAt,
                        content = state.content.trim()
                    )
                )
                _ui.update { it.copy(loading = false, success = res) }
            } catch (e: Exception) {
                _ui.update { it.copy(loading = false, error = e.message ?: "신청에 실패했어요.") }
            }
        }
    }
}

// handy extension
private inline fun <T> MutableStateFlow<T>.update(block: (T) -> T) {
    value = block(value)
}