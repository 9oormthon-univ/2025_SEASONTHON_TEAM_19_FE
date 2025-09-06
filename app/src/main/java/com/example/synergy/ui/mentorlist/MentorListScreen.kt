package com.example.synergy.ui.mentorlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

data class PageResponse<T>(
    val content: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int,
    val last: Boolean
)

data class MentorUser(
    val id: Int,
    val username: String,
    val email: String,
    val mentor: Boolean
)

@Composable
fun MentorListScreen(
    page: PageResponse<MentorUser>,
    // TODO: 서버에서 받은 실제 카테고리로 교체
    tabs: List<String> = listOf("전체", "카테", "고리", "카테", "고리"),
    onRequestMentoring: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {

            // TabRow
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 16.dp,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onSurface,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Color.Black
                    )
                },
                divider = { HorizontalDivider(color = Color(0x14000000)) }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                if (index == 0) "전체" else title,
                                fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal
                            )
                        },
                        selectedContentColor = Color.Black,
                        unselectedContentColor = Color(0xFF9E9E9E)
                    )
                }
            }

            // 리스트 (탭 필터가 실제로 필요하면 여기서 분기)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 96.dp)
            ) {
                items(
                    items = page.content,
                    key = { it.id }
                ) { user ->
                    MentorItem(user = user)
                    HorizontalDivider(color = Color(0x14000000))
                }
            }
        }

        // 하단 CTA
        Button(
            onClick = { onRequestMentoring() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,   // 배경색
                contentColor = Color.White      // 텍스트 색
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
private fun MentorItem(user: MentorUser) {
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
                .background(Color(0xFFE0E0E0))
        )

        Spacer(Modifier.width(12.dp))

        Column(Modifier.weight(1f)) {
            // 카테고리 뱃지
            Surface(
                color = Color(0x0F000000),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = "카테고리",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF7A7A7A),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            Spacer(Modifier.height(6.dp))

            // 멘토이름
            Text(
                text = user.username,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(2.dp))

            // 내용 (지금은 이메일)
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
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

@Preview(showBackground = true)
@Composable
private fun PreviewMentorListScreen() {
    val sample = PageResponse(
        content = listOf(
            MentorUser(5, "mentor3", "mentor3@example.com", true),
            MentorUser(2, "mentor2", "mentor2@example.com", true),
            MentorUser(1, "mentor1", "mentor1@example.com", true),
        ),
        pageNumber = 0, pageSize = 10, totalElements = 3, totalPages = 1, last = true
    )
    MaterialTheme { MentorListScreen(page = sample) }
}