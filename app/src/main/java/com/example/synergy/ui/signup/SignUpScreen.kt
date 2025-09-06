package com.example.synergy.ui.signup

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Text

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = viewModel(),
    onSignInClick: () -> Unit,
) {
    Text("회원가입 화면입니다.")
}