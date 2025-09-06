package com.example.synergy.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.synergy.ui.theme.Green1
import com.example.synergy.ui.theme.Green2
import com.example.synergy.ui.theme.Green3
import com.example.synergy.ui.theme.Green4
import com.example.synergy.ui.theme.Green5
import com.example.synergy.ui.theme.Green6
import com.example.synergy.ui.theme.Green7
import com.example.synergy.ui.theme.Green8
import com.example.synergy.ui.theme.MainGreen

fun categoryColor(code: String): Color {
    return when (code) {
        "AI" -> Green1
        "DIGITAL_UTIL" -> Green2
        "HOBBY" -> Green3
        "JOB_WORK" -> Green4
        "CARE" -> Green5
        "EXERCISE" -> Green6
        "LIFE" -> Green7
        "MIND" -> Green8
        else -> MainGreen
    }
}

@Composable
fun CategoryChip(code: String, name: String) {
    Surface(
        color = categoryColor(code),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}