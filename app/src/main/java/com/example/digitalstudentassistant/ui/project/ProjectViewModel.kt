package com.example.digitalstudentassistant.ui.project

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstudentassistant.data.database.ProjectEntity
import com.example.digitalstudentassistant.data.database.ProjectsDao
import com.example.digitalstudentassistant.data.database.ProjectsDatabase
import com.example.digitalstudentassistant.data.models.requests.ProjectRequest
import com.example.digitalstudentassistant.data.models.responses.ProjectResponse
import com.example.digitalstudentassistant.data.repositories.CVRepositoryImpl
import com.example.digitalstudentassistant.data.repositories.ProjectRepositoryImpl
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.models.CV
import com.example.digitalstudentassistant.domain.repositories.CVRepository
import com.example.digitalstudentassistant.domain.repositories.ProjectRepository
import com.example.digitalstudentassistant.ui.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProjectViewModel(application: Application) : AndroidViewModel(application) {

    private val projectRepository: ProjectRepository
    private val projectCreateStateFlow : MutableStateFlow<UIState<ProjectResponse, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val publicProjectCreateStateFlow = projectCreateStateFlow.asStateFlow()

    init {
        val projectsDao = ProjectsDatabase.create(application).projectsDao()
        projectRepository = ProjectRepositoryImpl(projectsDao, application.applicationContext)
    }

    fun addProject(projectEntity: ProjectEntity){
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.addProjectDB(projectEntity)
        }
    }

    fun createProject(project: ProjectRequest){
        viewModelScope.launch{
            projectCreateStateFlow.value = UIState.Loading
            val result = projectRepository.createProject(project)
            projectCreateStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }

    }


}