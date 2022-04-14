package com.example.digitalstudentassistant.ui


sealed class UIState<out S: Any?, out E: Any?> {
    object NothingDo : UIState<Nothing, Nothing>()
    object Loading : UIState<Nothing, Nothing>()
    data class Success<out S: Any?>(val data : S) : UIState<S, Nothing>()
    data class Error<out E: Any?>(val data: E) : UIState<Nothing, E>()
}