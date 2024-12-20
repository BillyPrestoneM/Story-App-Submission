package com.example.storyappsubmission.domain.usecase

import com.example.storyappsubmission.data.model.DetailStoryResponse
import com.example.storyappsubmission.di.utils.ResultUtil
import com.example.storyappsubmission.domain.repository.DetailRepository

class GetDetailStoryUseCase(private val detailRepository: DetailRepository) {
    suspend operator fun invoke(id: String) : ResultUtil<DetailStoryResponse> {
        return detailRepository.getDetailStory(id)

    }

}