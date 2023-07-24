package com.jaya.app.core.common

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val progress: Int? = null,
    val state: Boolean = false,
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String? = null, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(progress: Int? = null, state: Boolean = false) :
        Resource<T>(progress = progress, state = state)
}