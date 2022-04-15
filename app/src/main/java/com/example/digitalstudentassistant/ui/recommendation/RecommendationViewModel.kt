package com.example.digitalstudentassistant.ui.recommendation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstudentassistant.data.models.responses.ProjectResponse
import com.example.digitalstudentassistant.data.repositories.ProjectsRepositoryImpl
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.repositories.ProjectsRepository
import com.example.digitalstudentassistant.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecommendationViewModel(application: Application) : AndroidViewModel(application) {
    private val projectsRepository: ProjectsRepository = ProjectsRepositoryImpl(application.applicationContext)
    private val recommendedStateFlow: MutableStateFlow<UIState<List<ProjectResponse>, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val recommendedStateFlowPublic = recommendedStateFlow.asStateFlow()

    fun getRecommended(){
        viewModelScope.launch {
            recommendedStateFlow.value = UIState.Loading
            val result = projectsRepository.getRecommendProjects()
            val projectList = mutableListOf<ProjectResponse>()
            recommendedStateFlow.value = when(result){
                is OperationResult.Success -> {
                    for (i in result.data.indices){
                        projectList.add(
                            ProjectResponse(result.data[i].id,
                            result.data[i].title,
                            result.data[i].description,
                            result.data[i].communication,
                            result.data[i].creatorId,
                            result.data[i].tags)
                        )
                    }
                    UIState.Success(projectList)
                }
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }
}