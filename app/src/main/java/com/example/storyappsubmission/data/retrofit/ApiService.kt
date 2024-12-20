package com.example.storyappsubmission.data.retrofit

import com.example.storyappsubmission.data.model.AddNewStoryResponse
import com.example.storyappsubmission.data.model.DetailStoryResponse
import com.example.storyappsubmission.data.model.GetAllStoryResponse
import com.example.storyappsubmission.data.model.LoginResponse
import com.example.storyappsubmission.data.model.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun registerCall(@Field("name") name:String , @Field("email") email:String , @Field("password") password:String): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun loginCall(@Field("email") email:String , @Field("password") password:String): Response<LoginResponse>

    @Multipart
    @POST("stories")
    suspend fun addStoryCall(@Part file: MultipartBody.Part, @Part("description") description: RequestBody): Response<AddNewStoryResponse>

    @GET("stories")
    suspend fun getStoriesCall(): GetAllStoryResponse

    @GET("stories/{id}")
    suspend fun getDetailStoryCall(@Path("id") id: String): Response<DetailStoryResponse>
}