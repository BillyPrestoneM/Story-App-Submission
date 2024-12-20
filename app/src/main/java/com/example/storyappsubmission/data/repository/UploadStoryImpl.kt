package com.example.storyappsubmission.data.repository

import com.example.storyappsubmission.data.model.AddNewStoryResponse
import com.example.storyappsubmission.data.retrofit.ApiService
import com.example.storyappsubmission.di.utils.ResultUtil
import com.example.storyappsubmission.domain.repository.UploadStoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UploadStoryImpl(private val apiService: ApiService) : UploadStoryRepository {
    override suspend fun uploadStory(file: File, description: String): Flow<ResultUtil<AddNewStoryResponse>> {
        return flow {
            try {
                emit(ResultUtil.Loading)
                val descriptionRequestBody = description.toRequestBody("text/plain".toMediaType())
                val imageRequestBody = file.asRequestBody("image/jpeg".toMediaType())
                val imagePart = MultipartBody.Part.createFormData("photo", file.name, imageRequestBody)

                val response = apiService.addStoryCall(imagePart, descriptionRequestBody)

                if (response.isSuccessful && response.body() != null) {
                    emit(ResultUtil.Success(response.body()!!))
                } else {
                    emit(ResultUtil.Error("Upload Failed: ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(ResultUtil.Error("Exception occurred: ${e.message}"))
            }
        }
    }
}