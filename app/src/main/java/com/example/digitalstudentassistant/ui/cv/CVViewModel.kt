package com.example.digitalstudentassistant.ui.cv

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstudentassistant.data.UserPrefsStorage
import com.example.digitalstudentassistant.data.models.responses.LoginResponse
import com.example.digitalstudentassistant.data.network.ApiProvider
import com.example.digitalstudentassistant.data.repositories.CVRepositoryImpl
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.models.CV
import com.example.digitalstudentassistant.domain.repositories.CVRepository
import com.example.digitalstudentassistant.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CVViewModel(application: Application) : AndroidViewModel(application) {
    private var apiService = ApiProvider(application.applicationContext).apiService
    private var userPrefsStorage = UserPrefsStorage(application.applicationContext)
    private var cvRepository: CVRepository = CVRepositoryImpl(apiService, userPrefsStorage)
    private val cVStateFlow : MutableStateFlow<UIState<CV, String?>> =
        MutableStateFlow(UIState.Loading)
    val publicCVStateFlow = cVStateFlow.asStateFlow()

    fun addCV(cv: CV){
        viewModelScope.launch {
            val result = cvRepository.addCV(cv)
            cVStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }
}