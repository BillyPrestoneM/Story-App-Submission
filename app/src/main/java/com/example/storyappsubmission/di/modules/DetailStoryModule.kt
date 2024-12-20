package com.example.storyappsubmission.di.modules

import com.example.storyappsubmission.data.repository.GetDetailStoryImpl
import com.example.storyappsubmission.data.retrofit.ApiConfig
import com.example.storyappsubmission.domain.repository.DetailRepository
import com.example.storyappsubmission.domain.usecase.GetDetailStoryUseCase
import com.example.storyappsubmission.presentation.viewmodel.DetailStoryViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val DetailStoryModule = module {
    single<DetailRepository> { GetDetailStoryImpl(get(),get()) }
    single { ApiConfig.getApiService() }
    single { GetDetailStoryUseCase(get()) }
    viewModel { DetailStoryViewModelImpl(get(),get()) }


}