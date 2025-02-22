package com.example.texttoimageapi.imageApi.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.texttoimageapi.imageApi.domain.model.ImageResponse
import com.example.texttoimageapi.imageApi.domain.use_cases.generate_image_use_case.GenerateImageUseCase
import com.example.texttoimageapi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val generateImageUseCase: GenerateImageUseCase
) : ViewModel() {

    private val _imageState = MutableStateFlow<Resource<List<ImageResponse>>>(Resource.Idle)
    val imageState: StateFlow<Resource<List<ImageResponse>>> = _imageState

    fun fetchImage(prompt: String) {
        viewModelScope.launch {
            _imageState.value = Resource.Loading // Set loading state
            _imageState.value = generateImageUseCase(prompt) // Fetch image from use case
        }
    }
}
