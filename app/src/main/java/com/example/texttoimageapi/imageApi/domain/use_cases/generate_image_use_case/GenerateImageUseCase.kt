package com.example.texttoimageapi.imageApi.domain.use_cases.generate_image_use_case

import com.example.texttoimageapi.imageApi.domain.model.ImageResponse
import com.example.texttoimageapi.imageApi.domain.repository.ImageRepository
import com.example.texttoimageapi.util.Resource
import javax.inject.Inject

class GenerateImageUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(prompt: String): Resource<List<ImageResponse>> {
        return repository.generateImage(prompt)
    }
}