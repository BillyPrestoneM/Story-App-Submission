package com.example.storyappsubmission.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyappsubmission.data.model.ListStoryItem
import com.example.storyappsubmission.di.utils.ResultUtil
import com.example.storyappsubmission.domain.usecase.GetStoryUseCase
import com.example.storyappsubmission.domain.usecase.GetTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModelImpl(private val getStoryUseCase: GetStoryUseCase, private val getTokenUseCase: GetTokenUseCase) : ViewModel() {

    private val _storyList = MutableLiveData<ResultUtil<List<ListStoryItem>>>()
    val storyList: LiveData<ResultUtil<List<ListStoryItem>>> = _storyList

    private val _token = MutableStateFlow<String?>(null)
    val token : StateFlow<String?> = _token

    fun fetchStories() {
        viewModelScope.launch {
            if (getTokenUseCase() == null) {
                _storyList.value = ResultUtil.Error("No token found")
                return@launch
            }
            getStoryUseCase().collect { result ->
                _storyList.value = result
            }
        }

    }
}