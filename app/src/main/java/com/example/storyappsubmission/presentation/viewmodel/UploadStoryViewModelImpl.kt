package com.example.storyappsubmission.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.storyappsubmission.di.utils.ResultUtil
import com.example.storyappsubmission.domain.usecase.UploadStoryUseCase
import kotlinx.coroutines.launch
import java.io.File

class UploadStoryViewModelImpl(
    private val uploadStoryUseCase: UploadStoryUseCase
) : ViewModel() {

    class Factory(private val uploadStoryUseCase: UploadStoryUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UploadStoryViewModelImpl::class.java)) {
                return UploadStoryViewModelImpl(uploadStoryUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> = _successMessage

    private val _navigateToMain = MutableLiveData<Boolean>()
    val navigateToMain: LiveData<Boolean> = _navigateToMain


    fun uploadStoryModel(description: String, image: File) {
        _isLoading.value = true
        viewModelScope.launch {

            uploadStoryUseCase( image, description).collect { result ->
                when (result) {
                    is ResultUtil.Success -> {
                        _isLoading.value = false
                        _successMessage.value = "Story uploaded successfully"
                        _navigateToMain.value = true
                    }
                    is ResultUtil.Error -> {
                        _isLoading.value = false
                        Log.e("UploadStory", "Error uploading story: ${result.message}")
                        _errorMessage.value = result.message
                    }
                    is ResultUtil.Loading -> {
                        _isLoading.value = true
                    }
                }
            }
        }
    }
}