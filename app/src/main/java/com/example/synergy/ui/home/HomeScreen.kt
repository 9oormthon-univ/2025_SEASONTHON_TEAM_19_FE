package com.example.synergy.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.synergy.R
import com.example.synergy.data.model.Mentor
import com.example.synergy.ui.component.SYNERGYButton
import com.example.synergy.ui.home.component.CardItem
import com.example.synergy.ui.home.component.FavoriteMentorItem
import com.example.synergy.ui.home.component.MentorImageItem
import com.example.synergy.ui.home.component.MentorItem
import com.example.synergy.ui.theme.SYNERGYTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Column(modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.screen_padding))) {
            HorizontalDivider()
            Spacer(modifier = Modifier.height(40.dp))
            MentoringBannerSection(Modifier)

            Spacer(modifier = Modifier.height(60.dp))
            FavoriteMentorSection(uiState.favoriteMentors) {

            }

            Spacer(modifier = Modifier.height(60.dp))
            MyMentoringSection(Modifier, uiState) {

            }
        }

        Spacer(modifier = Modifier.height(60.dp))
        MentoringRecommendSection(Modifier, uiState.recommendedMentors) {

        }

        Spacer(modifier = Modifier.height(60.dp))
        Column(modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.screen_padding))) {
            RecentMentors(Modifier, uiState.recentMentors)
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.screen_padding) * 4))
    }
}

@Composable
fun MentoringBannerSection(modifier: Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.mentoring),
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = modifier.width(10.dp))
            Column(
                modifier = modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.mentoring_guideline),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = stringResource(R.string.user_mentoring_recommend),
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(onClick = {},) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = "MentorRecommendButton"
                )
            }
        }
    }
}

@Composable
fun FavoriteMentorSection(
    mentors: List<Mentor>,
    onMoreClick: () -> Unit,
) {
    Text(text = stringResource(R.string.favorite_mentor), fontSize = 20.sp)

    mentors
        .take(6)
        .chunked(2)
        .forEachIndexed { rowIndex, rowItems ->
            Row(modifier = Modifier.fillMaxWidth()) {
                rowItems.forEachIndexed { colIndex, mentor ->
                    FavoriteMentorItem(
                        modifier = Modifier.weight(1f),
                        index = rowIndex * 2 + colIndex + 1,
                        mentor = mentor,
                    )
                }
            }
        }

    Spacer(modifier = Modifier.height(40.dp))
    SYNERGYButton(Modifier, R.string.more) {
        onMoreClick()
    }
}

@Composable
fun MyMentoringSection(
    modifier: Modifier,
    uiState: HomeUiState,
    onLectureRoom: () -> Unit,
) {
    var selectedItem by remember { mutableIntStateOf(0) }

    val itemList = when (selectedItem) {
        0 -> uiState.myMentoringList
        1 -> uiState.myLectures
        else -> emptyList()
    }

    Text(text = stringResource(R.string.my_mentoring), fontSize = 20.sp)

    Row {
        TextButton(
            onClick = { selectedItem = 0 },
            colors = ButtonDefaults.textButtonColors(
                contentColor = if (selectedItem == 0) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.surface
            )
        ) {
            Text(text = stringResource(R.string.lecture))
        }
        TextButton(
            onClick = { selectedItem = 1 },
            colors = ButtonDefaults.textButtonColors(
                contentColor = if (selectedItem == 1) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.surface
            )
        ) {
            Text(text = stringResource(R.string.mentor))
        }
    }

    LazyRow {
        item { Spacer(modifier = modifier.width(8.dp)) }
        items(itemList) { item ->
            CardItem(modifier, item)
            Spacer(modifier = modifier.width(20.dp))
        }
    }

    Spacer(modifier = Modifier.height(40.dp))
    SYNERGYButton(Modifier, R.string.move_to_my_lecture_room) {
        onLectureRoom()
    }
}

@Composable
fun MentoringRecommendSection(
    modifier: Modifier,
    mentors: List<Mentor>,
    onMoreClick: () -> Unit,
) {
    Text(
        text = stringResource(R.string.mentor_recommend),
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.screen_padding)),
        fontSize = 20.sp
    )

    mentors.take(5).forEach { mentor ->
        MentorItem(modifier, mentor)
    }

    Spacer(modifier = Modifier.height(40.dp))
    SYNERGYButton(
        Modifier.padding(horizontal = dimensionResource(R.dimen.screen_padding)),
        R.string.more
    ) {
        onMoreClick()
    }
}

@Composable
fun RecentMentors(
    modifier: Modifier,
    recentMentors: List<String>,
) {
    Text(text = stringResource(R.string.recent_mentors), fontSize = 20.sp)

    Spacer(modifier = Modifier.height(20.dp))
    LazyRow(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        items(recentMentors) { mentor ->
            MentorImageItem(modifier, mentor)
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SYNERGYTheme {
        HomeScreen()
    }
}