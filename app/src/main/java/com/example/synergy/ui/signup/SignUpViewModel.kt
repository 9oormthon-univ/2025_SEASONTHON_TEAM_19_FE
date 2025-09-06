package com.example.synergy.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synergy.AuthRepository
import com.example.synergy.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val repo = AuthRepository(RetrofitClient.authApi)

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    fun signUp(email: String, username: String, password: String) {
        _uiState.value = SignUpUiState(loading = true)
        viewModelScope.launch {
            val result = repo.signUp(email, username, password)
            _uiState.value = result.fold(
                onSuccess = { SignUpUiState(success = true) },
                onFailure = { SignUpUiState(error = it.message ?: "알 수 없는 오류") }
            )
        }
    }

    fun clearError() { _uiState.value = _uiState.value.copy(error = null) }
}