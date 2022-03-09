package com.example.digitalstudentassistant.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstudentassistant.data.models.responses.login.LoginResponse
import com.example.digitalstudentassistant.data.repositories.AuthRepositoryImpl
import com.example.digitalstudentassistant.domain.OperationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepositoryImpl): ViewModel() {

    private val loginStateFlow : MutableStateFlow<UIStateLogin> =
        MutableStateFlow(UIStateLogin.Loading)
    val publicLoginStateFlow = loginStateFlow.asStateFlow()

    private val registerStateFlow : MutableStateFlow<UIStateLogin> =
        MutableStateFlow(UIStateLogin.Loading)
    val publicRegisterStateFlow = registerStateFlow.asStateFlow()

    fun login(email: String, password: String){
        viewModelScope.launch {
            val result = authRepository.login(email, password)
            loginStateFlow.value = when(result){
                is OperationResult.Success -> UIStateLogin.Success(result.data)
                is OperationResult.Error -> UIStateLogin.Error(result.data)
            }
        }
    }

    fun register(email: String, nickname: String, password: String){
        viewModelScope.launch {
            val result = authRepository.register(email, nickname, password)
            registerStateFlow.value = when(result){
                is OperationResult.Success -> UIStateLogin.Success(result.data)
                is OperationResult.Error -> UIStateLogin.Error(result.data)
            }
        }
    }

    sealed class UIStateLogin {
        object Loading : UIStateLogin()
        class Error(val e: String?) : UIStateLogin()
        class Success(val loginResponse: LoginResponse) : UIStateLogin()
    }
}