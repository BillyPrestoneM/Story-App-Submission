package com.example.storyappsubmission.domain.usecase

import com.example.storyappsubmission.domain.repository.AuthRepository

class AuthSaveTokenUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(token: String, name: String) {
        authRepository.saveAuthToken(token, name)
    }

}