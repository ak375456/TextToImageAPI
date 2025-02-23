package com.example.texttoimageapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.texttoimageapi.imageApi.presentation.HomeScreen
import com.example.texttoimageapi.ui.theme.TextToImageAPITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TextToImageAPITheme {
                HomeScreen()
            }
        }
    }
}
