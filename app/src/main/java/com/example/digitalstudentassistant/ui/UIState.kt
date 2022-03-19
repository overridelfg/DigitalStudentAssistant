package com.example.digitalstudentassistant.ui

import com.example.digitalstudentassistant.data.models.responses.login.LoginResponse

sealed class UIState<out S : Any?, out E : Any?> {
    data class Success<out S : Any?>(val result: S) : UIState<S, Nothing>()

    object Loading : UIState<Nothing, Nothing>()

    data class Error<out S : Any?, out E : Any?>(val oldValue: S, val result: E) : UIState<S, E>()
    companion object {
        fun <S> success(data: S) = Success(data)

        fun loading() = Loading

        fun <S, E> error(oldValue: S, message: E) = Error(oldValue, message)
    }
}