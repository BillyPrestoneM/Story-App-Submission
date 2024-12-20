package com.example.storyappsubmission.data.repository

import com.example.storyappsubmission.data.model.ListStoryItem
import com.example.storyappsubmission.data.retrofit.ApiService
import com.example.storyappsubmission.di.utils.ResultUtil
import com.example.storyappsubmission.domain.repository.StoryRepository
import com.example.storyappsubmission.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetStoryImpl(private val apiService: ApiService, private val tokenRepository: TokenRepository) : StoryRepository {

    override suspend fun getStories(): Flow<ResultUtil<List<ListStoryItem>>> {
        return flow {
            try {
                emit(ResultUtil.Loading)
                val token = tokenRepository.getToken()
                if (token == null) {
                    emit(ResultUtil.Error("No token found"))
                    return@flow
                }

                val response = apiService.getStoriesCall()
                println("Response status: ${response.error}")
                val data = response.listStory
                if (data.isNullOrEmpty()) {
                    emit(ResultUtil.Error("No stories available"))
                } else {
                    emit(ResultUtil.Success(data))
                }
            } catch (e: Exception) {
                emit(ResultUtil.Error("Exception occurred: ${e.message}"))
            }
        }
    }

    override suspend fun getToken(): String? {
        return tokenRepository.getToken()
    }
}