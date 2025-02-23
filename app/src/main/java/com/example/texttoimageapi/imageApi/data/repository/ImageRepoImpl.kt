package com.example.texttoimageapi.imageApi.data.repository

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.example.texttoimageapi.imageApi.data.remote.ImageApiService
import com.example.texttoimageapi.imageApi.domain.model.ImageResponse
import com.example.texttoimageapi.imageApi.domain.repository.ImageRepository
import com.example.texttoimageapi.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.OutputStream
import javax.inject.Inject
import android.content.Context


class ImageRepositoryImpl @Inject constructor(
    private val apiService: ImageApiService,
    private val context: Context
) : ImageRepository {

    override suspend fun generateImage(prompt: String): Resource<List<ImageResponse>> {
        return try {
            val imageUrls = apiService.generateImage(mapOf("prompt" to prompt))

            val imageResponses = listOf(ImageResponse(imageUrl = imageUrls))

            Resource.Success(imageResponses)
        } catch (e: Exception) {

            Resource.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun saveImage(imageUrl: String): Resource<Unit> {
        return try {
            val bitmap = downloadImage(imageUrl)
            if (bitmap != null) {
                saveBitmapToGallery(bitmap)
                Resource.Success(Unit)
            } else {
                Resource.Error("Failed to download image")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to save image")
        }
    }

    private suspend fun downloadImage(url: String): Bitmap? = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        val inputStream = response.body?.byteStream()
        BitmapFactory.decodeStream(inputStream)
    }

    private fun saveBitmapToGallery(bitmap: Bitmap): Boolean {
        val filename = "GeneratedImage_${System.currentTimeMillis()}.jpg"
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
        }

        var outputStream: OutputStream? = null
        return try {
            val uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            uri?.let {
                outputStream = context.contentResolver.openOutputStream(uri)

                outputStream?.let { stream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    true
                } == true
            } == true
        } catch (e: Exception) {
            false
        } finally {
            outputStream?.close()
        }
    }
}