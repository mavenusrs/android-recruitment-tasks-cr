package com.netguru.codereview.ui.model

sealed  class ResultState<T> {
    data class Success<T>(val data: T): ResultState<T>()
    data class Failed<T>(val error: Throwable? = null) : ResultState<T>()
}