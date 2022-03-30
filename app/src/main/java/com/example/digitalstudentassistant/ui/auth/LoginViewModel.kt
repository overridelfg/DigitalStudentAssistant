package com.example.digitalstudentassistant.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstudentassistant.data.models.responses.login.LoginResponse
import com.example.digitalstudentassistant.data.repositories.AuthRepositoryImpl
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepositoryImpl): ViewModel() {

    private val loginStateFlow : MutableStateFlow<UIState<LoginResponse, String?>> =
        MutableStateFlow(UIState.Loading)
    val publicLoginStateFlow = loginStateFlow.asStateFlow()

    fun login(email: String, password: String){
        viewModelScope.launch {
            val result = authRepository.login(email, password)
            loginStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }

}