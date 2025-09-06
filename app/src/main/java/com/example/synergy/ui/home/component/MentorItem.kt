package com.example.synergy.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.synergy.R
import com.example.synergy.data.model.Mentor

@Composable
fun FavoriteMentorItem(index: Int, mentor: Mentor) {
    Row {
        Text(text = "$index.")
        Text(text = mentor.name)
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null
        )
    }
}