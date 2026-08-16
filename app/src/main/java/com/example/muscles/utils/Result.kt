package com.example.muscles.utils


sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception, val message: String = exception.message ?: "Unknown error") : Result<Nothing>()
    object Loading : Result<Nothing>()
    
    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error
    val isLoading: Boolean get() = this is Loading
    
    inline fun <R> map(transform: (T) -> R): Result<R> = when (this) {
        is Success -> Success(transform(data))
        is Error -> Error(exception, message)
        is Loading -> Loading
    }
    
    inline fun <R> flatMap(transform: (T) -> Result<R>): Result<R> = when (this) {
        is Success -> transform(data)
        is Error -> Error(exception, message)
        is Loading -> Loading
    }
    
    inline fun onSuccess(action: (T) -> Unit): Result<T> {
        if (this is Success) action(data)
        return this
    }
    
    inline fun onError(action: (Exception, String) -> Unit): Result<T> {
        if (this is Error) action(exception, message)
        return this
    }
    
    inline fun onLoading(action: () -> Unit): Result<T> {
        if (this is Loading) action()
        return this
    }
    
    suspend inline fun <R> fold(
        onSuccess: suspend (T) -> R,
        onError: suspend (Exception, String) -> R,
        onLoading: suspend () -> R
    ): R = when (this) {
        is Success -> onSuccess(data)
        is Error -> onError(exception, message)
        is Loading -> onLoading()
    }
}

fun <T> Result<T>.getOrNull(): T? = (this as? Result.Success<T>)?.data

fun <T> Result<T>.getOrElse(default: T): T = when (this) {
    is Result.Success -> data
    else -> default
}
