package com.example.storyappsubmission.domain.usecase

import com.example.storyappsubmission.data.model.LoginResponse
import com.example.storyappsubmission.di.utils.ResultUtil
import com.example.storyappsubmission.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val authRepository: AuthRepository)  {
    suspend operator fun invoke(email: String, password: String) : Flow<ResultUtil<List<LoginResponse>>> {
        return authRepository.login(email, password)
    }
}