package com.example.storyappsubmission.di.modules

import com.example.storyappsubmission.data.repository.GetStoryImpl
import com.example.storyappsubmission.data.retrofit.ApiConfig
import com.example.storyappsubmission.domain.repository.StoryRepository
import com.example.storyappsubmission.domain.usecase.GetStoryUseCase
import com.example.storyappsubmission.presentation.viewmodel.MainViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val StoryModule = module {
    single<StoryRepository> { GetStoryImpl(get(),get()) }
    single{ ApiConfig.getApiService()}
    single { GetStoryUseCase(get()) }
    viewModel { MainViewModelImpl(get(),get()) }

}