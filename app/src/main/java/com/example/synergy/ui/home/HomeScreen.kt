package com.example.synergy.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.synergy.R
import com.example.synergy.ui.home.component.FavoriteMentorItem
import com.example.synergy.ui.home.model.Mentor
import com.example.synergy.ui.theme.SYNERGYTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
) {
    val favoriteMentors by viewModel.favoriteMentors.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.screen_padding))
            .verticalScroll(scrollState)
    ) {
        HorizontalDivider()
        Spacer(modifier = Modifier.height(40.dp))
        MentorRecommendSection(Modifier)

        Spacer(modifier = Modifier.height(60.dp))
        FavoriteMentorSection(favoriteMentors)
    }
}

@Composable
fun MentorRecommendSection(modifier: Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = modifier
                    .padding(dimensionResource(R.dimen.screen_padding))
                    .size(60.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primary)
            )

            Column(verticalArrangement = Arrangement.Center) {
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
        }
    }
}

@Composable
fun FavoriteMentorSection(favoriteMentors: List<Mentor>) {
    Text(text = stringResource(R.string.favorite_mentor), fontSize = 20.sp)

    favoriteMentors.chunked(2).forEach { rowItems ->
        Row(modifier = Modifier.fillMaxWidth()) {
            rowItems.forEachIndexed { index, mentor ->
                FavoriteMentorItem(index, mentor)
            }
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