package com.example.storyappsubmission.data.retrofit

import com.example.storyappsubmission.data.storage.AuthPreferencesToken
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val authPreference: AuthPreferencesToken) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { authPreference.getToken() }
        println("Token in interceptor: $token")
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)

    }
}