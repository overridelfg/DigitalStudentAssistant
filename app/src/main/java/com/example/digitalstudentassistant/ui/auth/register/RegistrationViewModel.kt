package com.example.digitalstudentassistant.ui.auth.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstudentassistant.data.repositories.AuthRepositoryImpl
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.repositories.AuthRepository
import com.example.digitalstudentassistant.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {
    private val registerStateFlow: MutableStateFlow<UIState<Any, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val publicRegisterStateFlow = registerStateFlow.asStateFlow()
    private val authRepository: AuthRepository = AuthRepositoryImpl(application.applicationContext)

    fun register(
        email: String,
        nickname: String,
        phoneNumber: String,
        firstname: String,
        lastname: String,
        telegram: String,
        password: String
    ) {
        viewModelScope.launch {
            registerStateFlow.value = UIState.Loading
            val result = authRepository.register(
                email,
                nickname,
                phoneNumber,
                firstname,
                lastname,
                telegram,
                password
            )
            registerStateFlow.value = when (result) {
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }
}