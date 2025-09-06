package com.example.synergy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.synergy.data.model.Category
import com.example.synergy.ui.theme.SYNERGYTheme

class MainActivity : ComponentActivity() {

    companion object{
        var categories = emptyList<Category>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SYNERGYTheme(dynamicColor = false) {
                SYNERGYApp()
            }
        }
    }
}