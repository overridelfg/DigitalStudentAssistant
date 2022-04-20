package com.example.digitalstudentassistant.ui.cv

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstudentassistant.data.UserPrefsStorage
import com.example.digitalstudentassistant.data.models.requests.CVRequest
import com.example.digitalstudentassistant.data.models.responses.LoginResponse
import com.example.digitalstudentassistant.data.models.responses.toCV
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
    private val addCVStateFlow : MutableStateFlow<UIState<CV, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val publicAddCVStateFlow = addCVStateFlow.asStateFlow()

    private val getCVStateFlow : MutableStateFlow<UIState<List<CV>, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val publicGetCVStateFlow = getCVStateFlow.asStateFlow()

    private val updateCVStateFlow : MutableStateFlow<UIState<CV, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val publicUpdateCVStateFlow = updateCVStateFlow.asStateFlow()

    fun addCV(cv: CVRequest){
        viewModelScope.launch {
            addCVStateFlow.value = UIState.Loading
            val result = cvRepository.addCV(cv)
            addCVStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }

    fun getCV(userId: String){
        viewModelScope.launch {
            getCVStateFlow.value = UIState.Loading
            val result = cvRepository.getUserCV(userId)
            getCVStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }

    fun updateCV(idCV : String, cv: CVRequest){
        viewModelScope.launch {
            updateCVStateFlow.value = UIState.Loading
            val result = cvRepository.updateCV(idCV, cv)
            updateCVStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data.toCV())
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }
}