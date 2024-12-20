package com.example.storyappsubmission.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.storyappsubmission.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map


class AuthPreferencesToken(private val dataStore: DataStore<Preferences>) : TokenRepository {

    companion object {
        private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
        private val AUTH_NAME_KEY = stringPreferencesKey("auth_name")
    }

    override suspend fun saveAuthToken(token: String, name: String) {
        dataStore.edit {
            it[AUTH_TOKEN_KEY] = token
            it[AUTH_NAME_KEY] = name
        }
        println("Token saved: $token")
    }

    override suspend fun getToken():String? {
        return dataStore.data.map {
            it[AUTH_TOKEN_KEY]
        }.firstOrNull()
    }

    override suspend fun getName(): Flow<String?> {
        return dataStore.data.map {
            it[AUTH_NAME_KEY]
        }
    }

    override suspend fun clearSession() {
        dataStore.edit {
            it.clear()
        }
    }
}