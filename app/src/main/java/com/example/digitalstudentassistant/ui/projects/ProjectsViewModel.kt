package com.example.digitalstudentassistant.ui.projects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstudentassistant.data.database.ProjectEntity
import com.example.digitalstudentassistant.data.database.ProjectsDatabase
import com.example.digitalstudentassistant.data.models.responses.ProjectResponse
import com.example.digitalstudentassistant.data.models.responses.project.UserProjectResponse
import com.example.digitalstudentassistant.data.models.responses.project.toTagResponse
import com.example.digitalstudentassistant.data.repositories.ProjectRepositoryImpl
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.repositories.ProjectRepository
import com.example.digitalstudentassistant.ui.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProjectsViewModel(application: Application) : AndroidViewModel(application) {

    private val projectRepository: ProjectRepository
    private val projectsStateFlow: MutableStateFlow<UIState<List<ProjectResponse>, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val projectsStateFlowPublic = projectsStateFlow.asStateFlow()

    private val projectsSearchStateFlow: MutableStateFlow<UIState<List<ProjectResponse>, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val projectsSearchStateFlowPublic = projectsSearchStateFlow.asStateFlow()

    private val projectsSortByLikesStateFlow: MutableStateFlow<UIState<List<ProjectResponse>, String?>> =
        MutableStateFlow(UIState.NothingDo)
    val projectsSortByLikesStateFlowPublic = projectsSortByLikesStateFlow.asStateFlow()


    init {
        val projectsDao = ProjectsDatabase.create(application).projectsDao()
        projectRepository = ProjectRepositoryImpl(projectsDao, application.applicationContext)
    }

    fun loadAllProjectsFromDB() : Flow<List<ProjectEntity>> {
        return projectRepository.getAllProjectsFromDB()
    }

    fun showAllProjects(){
        viewModelScope.launch {
            projectsStateFlow.value = UIState.Loading
            val result = projectRepository.showAllProjects()
            projectsStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }

    fun getSortedProjects(){
        viewModelScope.launch {
            projectsSortByLikesStateFlow.value = UIState.Loading
            val result = projectRepository.getSortProjects()
            projectsSortByLikesStateFlow.value = when(result){
                is OperationResult.Success -> UIState.Success(result.data)
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }

    fun getProjectSearch(key: String){
        viewModelScope.launch {
            projectsStateFlow.value = UIState.Loading
            val result = projectRepository.getProjectSearch(key)
            val projectList = mutableListOf<ProjectResponse>()
            projectsStateFlow.value = when(result){
                is OperationResult.Success -> {
                    for (i in result.data.indices){
                        projectList.add(ProjectResponse(result.data[i].id,
                            result.data[i].title,
                            result.data[i].description,
                            result.data[i].communication,
                            result.data[i].creatorId,
                            result.data[i].tags))
                    }
                    UIState.Success(projectList)
                }
                is OperationResult.Error -> UIState.Error(result.data)
            }
        }
    }


}