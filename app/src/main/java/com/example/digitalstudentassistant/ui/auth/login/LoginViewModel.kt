package com.example.digitalstudentassistant.ui.auth.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstudentassistant.data.repositories.AuthRepositoryImpl
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.repositories.AuthRepository
import com.example.digitalstudentassistant.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val loginStateFlow: MutableStateFlow<UIState<Any, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val publicLoginStateFlow = loginStateFlow.asStateFlow()
    private val authRepository: AuthRepository = AuthRepositoryImpl(application.applicationContext)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginStateFlow.value = UIState.Loading
            val result = authRepository.login(email, password)
            loginStateFlow.value = when (result) {
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }

}