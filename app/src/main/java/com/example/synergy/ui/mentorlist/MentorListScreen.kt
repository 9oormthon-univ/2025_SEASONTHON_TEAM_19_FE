package com.example.synergy.ui.mentorlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.synergy.R.drawable.ic_bookmark
import com.example.synergy.R.drawable.ic_bookmark_selected
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel

data class PageResponse<T>(
    val content: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int,
    val last: Boolean
)

@Composable
fun MentorListScreen(
    viewModel: MentorListViewModel = viewModel()
) {
    val ui by viewModel.ui.collectAsState()

    val tabs = remember(ui.categories) { listOf("전체") + ui.categories.map { it.name } }

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {

            // TabRow
            ScrollableTabRow(
                selectedTabIndex = ui.selectedTabIndex,
                edgePadding = 16.dp,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSurface,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[ui.selectedTabIndex]),
                        color = Color.Black
                    )
                },
                divider = { HorizontalDivider(color = Color(0x14000000)) }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = ui.selectedTabIndex == index,
                        onClick = { viewModel.onTabSelected(index) },
                        text = {
                            Text(
                                title,
                                fontWeight = if (ui.selectedTabIndex == index) FontWeight.SemiBold else FontWeight.Normal
                            )
                        },
                        selectedContentColor = Color.Black,
                        unselectedContentColor = Color(0xFF9E9E9E)
                    )
                }
            }

            // 리스트
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 96.dp)
            ) {
                items(
                    items = ui.mentors,
                    key = { it.id }
                ) { user ->
                    MentorItem(user = user)
                    HorizontalDivider(color = Color(0x14000000))
                }

                if (ui.isLoading && ui.mentors.isEmpty()) {
                    item {
                        Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }

                if (ui.error != null && ui.mentors.isEmpty()) {
                    item {
                        Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                            Text(ui.error!!, color = Color(0xFFB00020))
                        }
                    }
                }
            }
        }

        // 하단 CTA
        Button(
            onClick = { /* TODO: 바텀시트*/ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4F4F4F),
                contentColor = Color(0xFFFFFFFF),
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp
            )
        ) {
            Text(
                text = "멘토링 받기",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 28.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun MentorItem(user: MentorUserDto) {
    var isBookmarked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 프로필 이미지
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFFB0B0B0))
        )

        Spacer(Modifier.width(12.dp))

        Column(Modifier.weight(1f)) {
            // 카테고리 뱃지
            Surface(
                color = Color(0xFFEEEEEE),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = "카테고리",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF666666),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            Spacer(Modifier.height(6.dp))

            // 멘토이름
            Text(
                text = user.username,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF323232),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(2.dp))

            // 내용 (지금은 이메일)
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF323232),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // 북마크 버튼
        IconButton(onClick = { isBookmarked = !isBookmarked }) {
            Icon(
                painter = painterResource(
                    id = if (isBookmarked) ic_bookmark_selected
                    else ic_bookmark
                ),
                contentDescription = "bookmark",
                tint = Color.Unspecified   // TODO: 색상 지정
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun PreviewMentorListScreen() {
//    val sample = PageResponse(
//        content = listOf(
//            MentorUserDto(5, "mentor3", "mentor3@example.com", true),
//            MentorUser(2, "mentor2", "mentor2@example.com", true),
//            MentorUser(1, "mentor1", "mentor1@example.com", true),
//        ),
//        pageNumber = 0, pageSize = 10, totalElements = 3, totalPages = 1, last = true
//    )
//    MaterialTheme { MentorListScreen() }
//}