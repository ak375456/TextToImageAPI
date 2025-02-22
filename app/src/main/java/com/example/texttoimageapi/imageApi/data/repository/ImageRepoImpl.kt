package com.example.texttoimageapi.imageApi.data.repository

import com.example.texttoimageapi.imageApi.data.remote.ImageApiService
import com.example.texttoimageapi.imageApi.domain.model.ImageResponse
import com.example.texttoimageapi.imageApi.domain.repository.ImageRepository
import com.example.texttoimageapi.util.Resource
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val apiService: ImageApiService
) : ImageRepository {

    override suspend fun generateImage(prompt: String): Resource<List<ImageResponse>> {
        return try {
            // Call the API and get the list of image URLs
            val imageUrls = apiService.generateImage(mapOf("prompt" to prompt))

            // Map the list of URLs to a list of ImageResponse objects
            val imageResponses = listOf(ImageResponse(imageUrl = imageUrls))

            // Return the result as a Resource.Success
            Resource.Success(imageResponses)
        } catch (e: Exception) {
            // Handle errors and return a Resource.Error
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}