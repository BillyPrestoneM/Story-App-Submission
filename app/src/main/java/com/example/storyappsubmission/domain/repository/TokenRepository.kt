package com.example.storyappsubmission.domain.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    suspend fun getToken(): String?
    suspend fun getName(): Flow<String?>
    suspend fun saveAuthToken(token: String, name: String)
    suspend fun clearSession()


}