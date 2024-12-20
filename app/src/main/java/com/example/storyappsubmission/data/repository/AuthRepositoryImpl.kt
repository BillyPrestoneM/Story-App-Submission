package com.example.storyappsubmission.data.repository

import android.util.Log
import com.example.storyappsubmission.data.model.LoginResponse
import com.example.storyappsubmission.data.retrofit.ApiService
import com.example.storyappsubmission.di.utils.ResultUtil
import com.example.storyappsubmission.domain.repository.AuthRepository
import com.example.storyappsubmission.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject

class AuthRepositoryImpl(
    private val apiService: ApiService, private val tokenRepository: TokenRepository
) : AuthRepository {


    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Flow<ResultUtil<List<LoginResponse>>> {
        return flow {
            try {
                emit(ResultUtil.Loading)
                val response = apiService.registerCall(name, email, password)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data == null) {
                        emit(ResultUtil.Error("Register Failed"))
                    } else {
                        emit(ResultUtil.Success(listOf()))
                    }
                }
            }catch (e: Exception){
                emit(ResultUtil.Error("Exception occurred: ${e.message}"))
            }
        }
    }
    override suspend fun getToken(): String? {
        return tokenRepository.getToken()

    }

    override suspend fun getName(): Flow<String?> {
        return tokenRepository.getName()
    }

    override suspend fun saveAuthToken(token: String, name: String) {
        tokenRepository.saveAuthToken(token, name)
    }

    override suspend fun clearSession() {
        tokenRepository.clearSession()
    }

    override suspend fun login(
        email: String,
        password: String
    ): Flow<ResultUtil<List<LoginResponse>>> {
        return flow {
            try {
                emit(ResultUtil.Loading)
                val response = apiService.loginCall(email, password)
                if (response.isSuccessful) {
                    val data = response.body()?.loginResult
                    if (data == null) {
                        emit(ResultUtil.Error("Login Failed"))
                    }
                    data?.token?.let {
                        tokenRepository.saveAuthToken(it, data.name.toString())
                    } ?: run {
                        emit(ResultUtil.Error("Login Failed"))
                        return@flow
                    }
                    emit(ResultUtil.Success(listOf()))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = if (!errorBody.isNullOrEmpty()) {
                        try {
                            val jsonObject = JSONObject(errorBody)
                            val message =
                                jsonObject.optJSONObject("error")?.optString("message")
                                    ?: "Unknown error"
                            message
                        } catch (e: Exception) {
                            "Error Occurred While Parsing Error Body"
                        }
                    } else {
                        "Login Failed: No Error Body"
                    }
                    emit(ResultUtil.Error(errorMessage))
                }
            } catch (e: Exception) {
                Log.e("LoginRepositoryImpl", "Exception: ${e.message}", e)
                emit(ResultUtil.Error("Exception occurred: ${e.message}"))
            }
        }
    }
}
