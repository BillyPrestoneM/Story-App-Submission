package com.example.storyappsubmission.domain.usecase

import com.example.storyappsubmission.data.model.AddNewStoryResponse
import com.example.storyappsubmission.di.utils.ResultUtil
import com.example.storyappsubmission.domain.repository.UploadStoryRepository
import kotlinx.coroutines.flow.Flow
import java.io.File

class UploadStoryUseCase(private val uploadStoryRepository: UploadStoryRepository) {
    suspend operator fun invoke( image: File, description: String): Flow<ResultUtil<AddNewStoryResponse>> {
        return uploadStoryRepository.uploadStory( image, description)
    }
}

