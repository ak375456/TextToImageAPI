package com.example.texttoimageapi.imageApi.data.remote


import com.example.texttoimageapi.imageApi.domain.model.ImageResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST



interface ImageApiService {
    @Headers(
        "X-RapidAPI-Key: YOUR_KEY_HERE",
        "X-RapidAPI-Host: sdxl.p.rapidapi.com"
    )
    @POST("v1/txt2img")
    suspend fun generateImage(@Body request: Map<String, String>): List<String>
}



