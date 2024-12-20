package com.example.storyappsubmission.di.utils

sealed class ResultUtil<out R> {
    data class Success<out T>(val data: T) : ResultUtil<T>()
    data class Error(val message: String) : ResultUtil<Nothing>()
    object Loading : ResultUtil<Nothing>()

}