package com.example.synergy.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.synergy.AuthRepository
import com.example.synergy.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SignInUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val token: String? = null
)

class SignInViewModel : ViewModel() {
    private val repo = AuthRepository(RetrofitClient.authApi)

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    fun signIn(username: String, password: String) {
        _uiState.value = SignInUiState(loading = true)
        viewModelScope.launch {
            val result = repo.signIn(username, password)
            _uiState.value = result.fold(
                onSuccess = { res: SignInResponse ->
                    SignInUiState(token = "${res.tokenType} ${res.accessToken}")
                },
                onFailure = {
                    SignInUiState(error = it.message ?: "로그인 실패")
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}