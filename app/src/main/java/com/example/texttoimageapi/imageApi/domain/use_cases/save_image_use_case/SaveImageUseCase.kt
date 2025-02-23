package com.example.texttoimageapi.imageApi.domain.use_cases.save_image_use_case

import com.example.texttoimageapi.imageApi.domain.repository.ImageRepository

import com.example.texttoimageapi.util.Resource
import javax.inject.Inject

class SaveImageUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(imageUrl: String): Resource<Unit> {
        return repository.saveImage(imageUrl)
    }
}