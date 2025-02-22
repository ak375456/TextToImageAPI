package com.example.texttoimageapi.imageApi.domain.repository

import com.example.texttoimageapi.imageApi.domain.model.ImageResponse
import com.example.texttoimageapi.util.Resource

interface ImageRepository {
    suspend fun generateImage(prompt: String): Resource<List<ImageResponse>>
}