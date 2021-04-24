package com.example.myapplication.helper

sealed class ResultWrapper<out T> {
    object Loading : ResultWrapper<Nothing>()
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class Error(val error: String) : ResultWrapper<Nothing>()
}
