package com.example.storyappsubmission.domain.usecase

import com.example.storyappsubmission.data.model.ListStoryItem
import com.example.storyappsubmission.di.utils.ResultUtil
import com.example.storyappsubmission.domain.repository.StoryRepository
import kotlinx.coroutines.flow.Flow

class GetStoryUseCase(private val storyRepository: StoryRepository) {
    suspend operator fun invoke(): Flow<ResultUtil<List<ListStoryItem>>> {
        return storyRepository.getStories()
    }

}