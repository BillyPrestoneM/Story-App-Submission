package com.example.storyappsubmission.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyappsubmission.data.model.LoginResponse
import com.example.storyappsubmission.di.utils.ResultUtil
import com.example.storyappsubmission.domain.usecase.AuthSaveTokenUseCase
import com.example.storyappsubmission.domain.usecase.GetTokenUseCase
import com.example.storyappsubmission.domain.usecase.GetUserNameUseCase
import com.example.storyappsubmission.domain.usecase.LoginUseCase
import com.example.storyappsubmission.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModelImpl(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getTokenUseCase: GetTokenUseCase,
) : AuthViewModel, ViewModel() {

    private val _user = MutableStateFlow<ResultUtil<List<LoginResponse>>?>(null)
    val user : StateFlow<ResultUtil<List<LoginResponse>>?> = _user

    private val _userName = MutableStateFlow<String?>(null)
    val userName : StateFlow<String?> = _userName

    private val _token = MutableStateFlow<String?>(null)
    val token : StateFlow<String?> = _token



    override suspend fun login(email: String, password: String) {
        loginUseCase(email, password).collect { result ->
            _user.value = result
        }
    }

    override suspend fun register(name: String, email: String, password: String) {
        registerUseCase(name, email, password).collect { result ->
            _user.value = result
        }

    }


    override suspend fun fetchUserName():String {
        getUserNameUseCase().collect {
            result ->
            _userName.value = result
        }
        return userName.value ?: ""
    }

    fun fetchToken() {
        viewModelScope.launch {
            _token.value = getTokenUseCase()

        }
    }


}