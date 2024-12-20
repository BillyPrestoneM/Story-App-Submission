package com.example.storyappsubmission.domain.usecase

import com.example.storyappsubmission.domain.repository.AuthRepository

class GetTokenUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): String? {
        return authRepository.getToken()
    }
}