package com.example.texttoimageapi.di

import android.content.Context
import com.example.texttoimageapi.imageApi.data.remote.ImageApiService
import com.example.texttoimageapi.imageApi.data.repository.ImageRepositoryImpl
import com.example.texttoimageapi.imageApi.domain.repository.ImageRepository
import com.example.texttoimageapi.imageApi.domain.use_cases.generate_image_use_case.GenerateImageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideImageApiService(): ImageApiService {
        return Retrofit.Builder()
            .baseUrl("https://sdxl.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideImageRepository(@ApplicationContext context: Context, apiService: ImageApiService): ImageRepository {
        return ImageRepositoryImpl(
            apiService,
            context = context
        )
    }

    @Provides
    @Singleton
    fun provideGenerateImageUseCase(repository: ImageRepository): GenerateImageUseCase {
        return GenerateImageUseCase(repository)
    }
    
    
}
