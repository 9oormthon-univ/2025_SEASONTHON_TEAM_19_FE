package com.example.synergy.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.synergy.R

@Composable
fun MentorImageItem(modifier: Modifier, mentor: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Image(
//            painter = painterResource(R.drawable.ic_launcher_background),
//            contentDescription = "MentorImage",
//            modifier = modifier
//                .size(100.dp)
//                .clip(CircleShape)
//        )
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color(0xFFB0B0B0))
        )

        Spacer(modifier = modifier.height(30.dp))
        Text(text = mentor, overflow = TextOverflow.Ellipsis)
    }
}