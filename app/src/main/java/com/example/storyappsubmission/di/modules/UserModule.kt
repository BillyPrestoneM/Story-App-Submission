package com.example.storyappsubmission.di.modules

import com.example.storyappsubmission.data.repository.AuthRepositoryImpl
import com.example.storyappsubmission.data.retrofit.ApiConfig
import com.example.storyappsubmission.data.retrofit.ApiService
import com.example.storyappsubmission.data.storage.AuthPreferencesToken
import com.example.storyappsubmission.domain.repository.AuthRepository
import com.example.storyappsubmission.domain.usecase.AuthSaveTokenUseCase
import com.example.storyappsubmission.domain.usecase.GetTokenUseCase
import com.example.storyappsubmission.domain.usecase.GetUserNameUseCase
import com.example.storyappsubmission.domain.usecase.LoginUseCase
import com.example.storyappsubmission.domain.usecase.RegisterUseCase
import com.example.storyappsubmission.presentation.viewmodel.AuthViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userModule = module{
    single<ApiService>{ ApiConfig.getApiService() }
    single<AuthRepository>{ AuthRepositoryImpl(get(), get()) }
    single{ GetTokenUseCase(get()) }
    single{ LoginUseCase(get()) }
    single{ AuthSaveTokenUseCase(get()) }
    single{ RegisterUseCase(get()) }
    single{ GetUserNameUseCase(get()) }
    single{ AuthPreferencesToken(get()) }

    viewModel{ AuthViewModelImpl(get(), get(), get(), get()) }

}