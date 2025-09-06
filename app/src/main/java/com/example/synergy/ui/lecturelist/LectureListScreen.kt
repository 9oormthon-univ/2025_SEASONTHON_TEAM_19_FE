package com.example.synergy.ui.lecturelist

import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.synergy.R

data class Lecture(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val bookmarked: Boolean = false
)

data class PageResponse<T>(
    val content: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int,
    val last: Boolean
)

@Composable
fun LectureListScreen(
    page: PageResponse<Lecture>,
    tabs: List<String> = listOf("전체", "카테", "고리", "카테", "고리"),
    onCardClick: (Lecture) -> Unit = {},
    onBookmarkToggle: (Lecture, Boolean) -> Unit = { _, _ -> }
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    // 카드 개별 상태
    // 서버 연동 시 ViewModel 상태로 교체
    var bookmarked by remember(page.content) {
        mutableStateOf(page.content.associate { it.id to it.bookmarked })
    }

    Column(Modifier.fillMaxSize()) {
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            edgePadding = 16.dp,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface,
            indicator = { positions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(positions[selectedTab]),
                    color = Color.Black
                )
            },
            divider = { HorizontalDivider(color = Color(0x14000000)) }
        ) {
            tabs.forEachIndexed { i, t ->
                Tab(
                    selected = selectedTab == i,
                    onClick = { selectedTab = i },
                    text = {
                        Text(
                            if (i == 0) "전체" else t,
                            fontWeight = if (selectedTab == i) FontWeight.SemiBold else FontWeight.Normal
                        )
                    },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color(0xFF9E9E9E)
                )
            }
        }

        val filtered = page.content.filter {
            selectedTab == 0 || it.category == tabs[selectedTab]
        }

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
                    lecture = lecture.copy(bookmarked = bookmarked[lecture.id] == true),
                    onClick = { onCardClick(lecture) },
                    onBookmarkClick = {
                        val newValue = !(bookmarked[lecture.id] ?: false)
                        bookmarked = bookmarked.toMutableMap().also { m -> m[lecture.id] = newValue }
                        onBookmarkToggle(lecture, newValue)
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
    onBookmarkClick: () -> Unit
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
                modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = if (lecture.bookmarked)
                            R.drawable.ic_bookmark_selected
                        else
                            R.drawable.ic_bookmark
                    ),
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
            text = lecture.description,
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
    val page = PageResponse(
        content = listOf(
            Lecture(1, "강연제목", "내용...", "카테고리"),
            Lecture(2, "강연제목", "내용...", "카테고리"),
            Lecture(3, "강연제목", "내용...", "카테고리"),
            Lecture(4, "강연제목", "내용...", "카테고리"),
            Lecture(5, "강연제목", "내용...", "다른카테"),
        ),
        pageNumber = 0, pageSize = 10, totalElements = 5, totalPages = 1, last = true
    )
    MaterialTheme { LectureListScreen(page = page) }
}