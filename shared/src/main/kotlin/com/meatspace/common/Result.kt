package com.meatspace.common

import kotlinx.serialization.Serializable

@Serializable
sealed class Result<out T> {
    @Serializable
    data class Success<T>(val data: T) : Result<T>()

    @Serializable
    data class Error(val message: String, val code: String? = null) : Result<Nothing>()

    fun isSuccess(): Boolean = this is Success
    fun isError(): Boolean = this is Error

    fun getOrNull(): T? = when (this) {
        is Success -> data
        is Error -> null
    }

    fun getOrThrow(): T = when (this) {
        is Success -> data
        is Error -> throw Exception(message)
    }
}

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: ErrorDetails? = null
)

@Serializable
data class ErrorDetails(
    val message: String,
    val code: String? = null,
    val field: String? = null
)
