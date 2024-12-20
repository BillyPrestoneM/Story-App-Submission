package com.example.storyappsubmission.di.modules

import com.example.storyappsubmission.data.storage.AuthPreferencesToken
import com.example.storyappsubmission.data.storage.DataStoreFactory.createDataStore
import com.example.storyappsubmission.domain.repository.TokenRepository
import org.koin.dsl.module

val dataStoreModule = module{
    single{createDataStore(get(), "auth_token_prefs")}
    single<TokenRepository>{AuthPreferencesToken(get())}
}
