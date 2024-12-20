package com.example.storyappsubmission.di.modules

import com.example.storyappsubmission.data.repository.UploadStoryImpl
import com.example.storyappsubmission.data.retrofit.ApiConfig
import com.example.storyappsubmission.data.retrofit.ApiService
import com.example.storyappsubmission.domain.repository.UploadStoryRepository
import com.example.storyappsubmission.domain.usecase.UploadStoryUseCase
import com.example.storyappsubmission.presentation.viewmodel.UploadStoryViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AddStoryModule = module {
    single<ApiService> { ApiConfig.getApiService() }
    single<UploadStoryRepository> { UploadStoryImpl(get()) }
    single { UploadStoryUseCase(get()) }
    viewModel { UploadStoryViewModelImpl(get()) }
}
