package com.example.synergy.ui.mentordetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.synergy.data.model.MentorDetail
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import com.example.synergy.SYNERGYApp
import com.example.synergy.data.model.Category
import com.example.synergy.ui.component.CategoryChip
import com.example.synergy.ui.component.LoadingScreen
import com.example.synergy.ui.theme.Gray60
import com.example.synergy.ui.theme.Gray80
import com.example.synergy.ui.theme.SYNERGYTheme
import com.example.synergy.ui.theme.White

@Composable
fun MentorDetailScreen(
    mentorId: Int,
    onApplyClick: () -> Unit = {},
    viewModel: MentorDetailViewModel = viewModel(),
) {
    LaunchedEffect(mentorId) { viewModel.load(mentorId) }

    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is MentorDetailUiState.Loading -> LoadingScreen()
        is MentorDetailUiState.Error -> ErrorView(
            message = (uiState as MentorDetailUiState.Error).message,
            onRetry = { viewModel.load(mentorId) }
        )
        is MentorDetailUiState.Success -> {
            val data = (uiState as MentorDetailUiState.Success).data
            MentorDetailContent(
                data = data,
                onApplyClick = onApplyClick
            )
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MentorDetailContent(
    data: MentorDetail,
    onApplyClick: () -> Unit
) {
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(20.dp)
    ) {
        // 상단 프로필
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color(0xFFD0D0D0))
                .align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = data.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(8.dp))

        // 카테고리 뱃지
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            data.categories.forEach { cat ->
                CategoryChip(code = cat.code, name = cat.category)
            }
        }

        Spacer(Modifier.height(16.dp))

        // 카드 – 학력/경력/소개
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp
            )
        ) {
            Column(Modifier.padding(16.dp)) {
                Section(title = "학력", body = data.education ?: "학력 정보가 없습니다.")
                Spacer(Modifier.height(24.dp))
                Section(title = "경력", body = data.career ?: "경력 정보가 없습니다.")
                Spacer(Modifier.height(24.dp))
                Section(title = "소개", body = data.introduction ?: "소개가 없습니다.")
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onApplyClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = White,
            )
        ) {
            Text("멘토링 신청하기")
        }

        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("불러오는 중 오류가 발생했습니다.\n$message", textAlign = TextAlign.Center)
        Spacer(Modifier.height(12.dp))
        OutlinedButton(onClick = onRetry) { Text("다시 시도") }
    }
}

@Composable
private fun Section(title: String, body: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = Color(0xFFB0B0B0)
    )
    Spacer(Modifier.height(6.dp))
    Text(
        text = body,
        style = MaterialTheme.typography.bodyMedium,
        lineHeight = 20.sp,
        color = Gray80
    )
}

@Preview(name = "상세 - 성공", showBackground = true)
@Composable
private fun MentorDetailContentPreview() {
    val sample = MentorDetail(
        id = 1,
        name = "멘토이름",
        categories = listOf(
            Category(code = "DIGITAL_UTIL", category = "디지털"),
            Category(code = "AI", category = "AI")
        ),
        education = "서울대학교 컴퓨터공학과",
        career = """
            2010~2018  삼성전자
            2018~2025  LG전자
        """.trimIndent(),
        introduction = "안녕하세요. 백엔드/모바일 멘토링을 하고 있습니다."
    )

    SYNERGYTheme {
        MentorDetailContent(
            data = sample,
            onApplyClick = {}
        )
    }
}