package com.example.storyappsubmission.domain.repository

import com.example.storyappsubmission.data.model.ListStoryItem
import com.example.storyappsubmission.di.utils.ResultUtil
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    suspend fun getStories(): Flow<ResultUtil<List<ListStoryItem>>>
    suspend fun getToken(): String?

}