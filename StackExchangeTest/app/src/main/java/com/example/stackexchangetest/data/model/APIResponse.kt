package com.example.stackexchangetest.data.model

sealed class APIResponse<out T : Any?> {
    data class Success<out T : Any?>(val data: T) : APIResponse<T>()
    data class Error(val message: String) : APIResponse<Nothing>()
}