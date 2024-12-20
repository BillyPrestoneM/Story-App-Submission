package com.example.storyappsubmission.data.repository

import com.example.storyappsubmission.data.model.DetailStoryResponse
import com.example.storyappsubmission.data.retrofit.ApiService
import com.example.storyappsubmission.di.utils.ResultUtil
import com.example.storyappsubmission.domain.repository.DetailRepository
import com.example.storyappsubmission.domain.repository.TokenRepository

class GetDetailStoryImpl(private val apiService: ApiService, private val tokenRepository: TokenRepository) : DetailRepository {
    override suspend fun getDetailStory(id: String): ResultUtil<DetailStoryResponse> {
        return try {
            val token = tokenRepository.getToken()
            if (token == null) {
                return ResultUtil.Error("No token found")
            }

            val response = apiService.getDetailStoryCall(id)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    ResultUtil.Success(data)
                } else {
                    ResultUtil.Error("No data found")
                }
            } else {
                ResultUtil.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            ResultUtil.Error("Exception occurred: ${e.message}")
        }
    }
}

