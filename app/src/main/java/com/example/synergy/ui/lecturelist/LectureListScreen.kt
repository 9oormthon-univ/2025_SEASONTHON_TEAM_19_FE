package com.example.synergy.ui.lecturelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.synergy.R
import com.example.synergy.data.model.Lecture
import com.example.synergy.ui.theme.SYNERGYTheme

@Composable
fun LectureListScreen(
    onCardClick: (Lecture) -> Unit = {},
    onBookmarkToggle: (Lecture, Boolean) -> Unit = { _, _ -> },
    viewModel: LectureListViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val categories: List<Pair<String, String>> = listOf(
        "전체" to "전체",
        "AI" to "AI",
        "DIGITAL_UTIL" to "디지털활용",
        "HOBBY" to "취미",
        "JOB_WORK" to "취업 & 일",
        "CARE" to "돌봄",
        "EXERCISE" to "운동",
        "LIFE" to "생활",
        "MIND" to "마음"
    )

    var selectedTab by remember { mutableIntStateOf(0) }

    Column(Modifier.fillMaxSize()) {
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            edgePadding = 16.dp,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface,
            indicator = { positions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(positions[selectedTab]),
                    color = Color.Black
                )
            },
            divider = { HorizontalDivider(color = Color(0x14000000)) }
        ) {
            categories.forEachIndexed { i, t ->
                Tab(
                    selected = selectedTab == i,
                    onClick = { selectedTab = i },
                    text = {
                        Text(
                            t.second,
                            fontWeight = if (selectedTab == i) FontWeight.SemiBold else FontWeight.Normal
                        )
                    },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color(0xFF9E9E9E)
                )
            }
        }


        val filtered =
//            if (selectedTab == 0) {
//            uiState.content
//        } else {
            uiState.content.filter { selectedTab == 0 || it.category == categories[selectedTab].first }
//        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = filtered,
                key = { it.id }
            ) { lecture ->
                LectureCard(
                    lecture = lecture,
                    onClick = { onCardClick(lecture) },
                    onBookmarkClick = {

                    }
                )
            }
        }
    }
}

@Composable
private fun LectureCard(
    lecture: Lecture,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // 썸네일
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f) // 정사각형 비율 유지
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFB0B0B0))
        ) {
            // 카테고리 라벨
            Surface(
                color = Color(0xFFEEEEEE),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.TopStart)
            ) {
                Text(
                    text = lecture.category,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF666666),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            // 북마크
            IconButton(
                onClick = onBookmarkClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_bookmark),
                    contentDescription = "bookmark",
                    tint = Color.Unspecified   // TODO: 색상 지정
                )
            }

            // 클릭 영역
            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Transparent)
                    .then(Modifier) // 클릭 시 바텀 시트: clickable 추가
            )
        }

        Spacer(Modifier.height(8.dp))

        // 강연 제목
        Text(
            text = lecture.title,
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF323232),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(2.dp))
        // 내용
        Text(
            text = lecture.content ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF323232),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun PreviewLectureListScreen() {
    SYNERGYTheme { LectureListScreen() }
}