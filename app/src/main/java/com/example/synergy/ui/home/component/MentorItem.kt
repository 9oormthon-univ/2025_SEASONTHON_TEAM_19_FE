package com.example.synergy.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.synergy.R
import com.example.synergy.data.model.Mentor

@Composable
fun MentorItem(
    modifier: Modifier,
    mentor: Mentor,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.screen_padding)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = modifier

                .size(60.dp)
                .clip(CircleShape),
        )

        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(text = mentor.name)
            Spacer(modifier = modifier.height(5.dp))
            Text(
                text = mentor.introduction ?: "",
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
        }
    }
}