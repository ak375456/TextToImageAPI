package com.example.texttoimageapi.imageApi.data.remote



import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST



interface ImageApiService {
    @Headers(
        "X-RapidAPI-Key:eebe42a5c5mshac54fd6db7d1552p19bb75jsn8ed2a745a348",
        "X-RapidAPI-Host: sdxl.p.rapidapi.com"
    )
    @POST("v1/txt2img")
    suspend fun generateImage(@Body request: Map<String, String>): List<String>
}



