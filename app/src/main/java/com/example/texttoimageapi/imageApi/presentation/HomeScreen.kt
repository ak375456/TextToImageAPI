package com.example.texttoimageapi.imageApi.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.texttoimageapi.imageApi.domain.model.ImageResponse
import com.example.texttoimageapi.util.Resource

@Composable
fun HomeScreen(
    viewModel: ImageViewModel = hiltViewModel()
) {
    var prompt by remember { mutableStateOf("") }
    val imageState by viewModel.imageState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = prompt,
            onValueChange = { prompt = it },
            label = { Text("Enter Prompt") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.fetchImage(prompt) },
            enabled = prompt.isNotBlank()
        ) {
            Text("Generate Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (imageState) {
            is Resource.Loading -> {
                CircularProgressIndicator()
            }
            is Resource.Success -> {
                val imageUrl = (imageState as Resource.Success<List<ImageResponse>>)
                    .data
                    .firstOrNull()
                    ?.imageUrl
                    ?.firstOrNull()

                Log.d("URLLL", imageUrl.toString())

                if (imageUrl != null) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Generated Image",
                        modifier = Modifier
                            .size(250.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                } else {
                    Text("No image URL found", color = Color.Red)
                }
            }
            is Resource.Error -> {
                Text(
                    text = (imageState as Resource.Error).message,
                    color = Color.Red
                )
            }
            is Resource.Idle -> {}
        }
    }
}