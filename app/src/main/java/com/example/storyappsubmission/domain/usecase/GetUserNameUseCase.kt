package com.example.storyappsubmission.domain.usecase

import com.example.storyappsubmission.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class GetUserNameUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Flow<String?> {
        return authRepository.getName()
    }

}