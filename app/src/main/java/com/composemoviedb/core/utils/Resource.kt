package com.composemoviedb.core.utils

sealed interface Resource<out T> {
    data object Loading : Resource<Nothing>
    data class Success<T>(val data: T? = null) : Resource<T>
    data class Error(val errorMessage: String? = null) : Resource<Nothing>
}