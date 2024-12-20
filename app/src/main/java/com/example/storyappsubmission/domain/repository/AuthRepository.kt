package com.example.storyappsubmission.domain.repository

import com.example.storyappsubmission.data.model.LoginResponse
import com.example.storyappsubmission.data.model.RegisterResponse
import com.example.storyappsubmission.di.utils.ResultUtil
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login (email: String, password: String): Flow<ResultUtil<List<LoginResponse>>>
    suspend fun register (name: String, email: String, password: String): Flow<ResultUtil<List<LoginResponse>>>
    suspend fun getToken(): String?
    suspend fun getName(): Flow<String?>
    suspend fun saveAuthToken(token: String, name: String)
    suspend fun clearSession()


}