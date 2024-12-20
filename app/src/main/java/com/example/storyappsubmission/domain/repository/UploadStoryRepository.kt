package com.example.storyappsubmission.domain.repository

import com.example.storyappsubmission.data.model.AddNewStoryResponse
import com.example.storyappsubmission.di.utils.ResultUtil
import kotlinx.coroutines.flow.Flow
import java.io.File

interface UploadStoryRepository {
    suspend fun uploadStory(file: File, description: String): Flow<ResultUtil<AddNewStoryResponse>>
}