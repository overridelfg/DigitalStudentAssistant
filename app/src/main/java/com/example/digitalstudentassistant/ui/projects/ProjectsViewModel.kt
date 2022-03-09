package com.example.digitalstudentassistant.ui.projects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstudentassistant.data.database.ProjectEntity
import com.example.digitalstudentassistant.data.database.ProjectsDatabase
import com.example.digitalstudentassistant.data.repositories.ProjectRepositoryImpl
import com.example.digitalstudentassistant.domain.repositories.ProjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProjectsViewModel(application: Application) : AndroidViewModel(application) {

    private val projectRepository: ProjectRepository
    private val projectsStateFlow: MutableStateFlow<UIState> =
        MutableStateFlow(UIState.Loading)
    val projectsStateFlowPublic = projectsStateFlow.asStateFlow()

    init {
        val projectsDao = ProjectsDatabase.create(application).projectsDao()
        projectRepository = ProjectRepositoryImpl(projectsDao)
    }

    fun loadAllProjectsFromDB() : Flow<List<ProjectEntity>> {
        return projectRepository.getAllProjectsFromDB()
    }

    sealed class UIState {
        object Loading : UIState()
        class Error(val e: Exception) : UIState()
        class Success(val projects: List<ProjectEntity>) : UIState()
    }

}