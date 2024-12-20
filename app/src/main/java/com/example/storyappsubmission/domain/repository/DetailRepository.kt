package com.example.storyappsubmission.domain.repository

import com.example.storyappsubmission.data.model.DetailStoryResponse
import com.example.storyappsubmission.di.utils.ResultUtil
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getDetailStory(id: String): ResultUtil<DetailStoryResponse>
}