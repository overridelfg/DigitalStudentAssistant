package com.example.digitalstudentassistant.ui.courses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstudentassistant.data.models.responses.CourseResponse
import com.example.digitalstudentassistant.data.repositories.CoursesRepositoryImpl
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.models.CV
import com.example.digitalstudentassistant.domain.repositories.CoursesRepository
import com.example.digitalstudentassistant.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CoursesViewModel(application: Application) : AndroidViewModel(application){
    private val coursesRepository : CoursesRepository = CoursesRepositoryImpl(application.applicationContext)

    private val getCursesStateFlow : MutableStateFlow<UIState<List<CourseResponse>, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val publicGetCursesStateFlow = getCursesStateFlow.asStateFlow()

    fun getAllCourses(){
        viewModelScope.launch {
            getCursesStateFlow.value = UIState.Loading
            val result = coursesRepository.getAllCourses()
            getCursesStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }
}