package com.example.digitalstudentassistant.ui.projectdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstudentassistant.data.database.ProjectEntity
import com.example.digitalstudentassistant.data.database.ProjectsDatabase
import com.example.digitalstudentassistant.data.models.requests.UpdateProjectRequest
import com.example.digitalstudentassistant.data.models.responses.ProjectResponse
import com.example.digitalstudentassistant.data.repositories.ProjectRepositoryImpl
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.repositories.ProjectRepository
import com.example.digitalstudentassistant.ui.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ProjectDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val projectRepository: ProjectRepository
    private val projectUpdateStateFlow: MutableStateFlow<UIState<ProjectResponse, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val projectUpdateStateFlowPublic = projectUpdateStateFlow.asStateFlow()

    private val addLikeStateFlow: MutableStateFlow<UIState<ProjectResponse, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val addLikeStateFlowPublic = addLikeStateFlow.asStateFlow()

    private val removeLikeStateFlow: MutableStateFlow<UIState<ProjectResponse, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val removeLikeStateFlowPublic = removeLikeStateFlow.asStateFlow()


    init {
        val projectsDao = ProjectsDatabase.create(application).projectsDao()
        projectRepository = ProjectRepositoryImpl(projectsDao, application.applicationContext)
    }

    fun getProjectFromDB(projectId: Int) : Flow<ProjectEntity> {
        return projectRepository.getProjectFromDB(projectId)
    }

    fun updateProject(idProject: String, updateProjectRequest: UpdateProjectRequest){
        viewModelScope.launch {
            projectUpdateStateFlow.value = UIState.Loading
            val result = projectRepository.updateProject(idProject, updateProjectRequest)
            projectUpdateStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }

    fun addLike(idProject: String){
        viewModelScope.launch {
            addLikeStateFlow.value = UIState.Loading
            val result = projectRepository.addLike(projectId = idProject)
            addLikeStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }

    fun removeLike(idProject: String){
        viewModelScope.launch {
            removeLikeStateFlow.value = UIState.Loading
            val result = projectRepository.addLike(projectId = idProject)
            removeLikeStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }
}