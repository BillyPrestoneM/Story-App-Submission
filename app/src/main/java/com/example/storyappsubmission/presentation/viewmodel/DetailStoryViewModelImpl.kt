package com.example.storyappsubmission.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyappsubmission.data.model.DetailStoryResponse
import com.example.storyappsubmission.di.utils.ResultUtil
import com.example.storyappsubmission.domain.usecase.GetDetailStoryUseCase
import com.example.storyappsubmission.domain.usecase.GetTokenUseCase
import kotlinx.coroutines.launch

class DetailStoryViewModelImpl(
    private val getDetailStoryUseCase: GetDetailStoryUseCase,
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    private val _detailStory = MutableLiveData<ResultUtil<DetailStoryResponse>>()
    val detailStory: LiveData<ResultUtil<DetailStoryResponse>> = _detailStory

    fun fetchDetailStory(id: String) {
        viewModelScope.launch {
            val token = getTokenUseCase()
            if (token == null) {
                _detailStory.value = ResultUtil.Error("No token found")
                return@launch
            }

            try {
                _detailStory.value = ResultUtil.Loading
                val result = getDetailStoryUseCase(id)
                _detailStory.value = result
            } catch (e: Exception) {
                _detailStory.value = ResultUtil.Error("Exception occurred: ${e.message}")
            }
        }
    }

}
