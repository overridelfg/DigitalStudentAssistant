package com.example.digitalstudentassistant.ui.project

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalstudentassistant.data.database.ProjectEntity
import com.example.digitalstudentassistant.data.database.ProjectsDao
import com.example.digitalstudentassistant.data.database.ProjectsDatabase
import com.example.digitalstudentassistant.data.repositories.ProjectRepositoryImpl
import com.example.digitalstudentassistant.domain.repositories.ProjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectViewModel(application: Application) : AndroidViewModel(application) {

    private val projectRepository: ProjectRepository

    init {
        val projectsDao = ProjectsDatabase.create(application).projectsDao()
        projectRepository = ProjectRepositoryImpl(projectsDao)
    }

    fun addProject(projectEntity: ProjectEntity){
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.addProjectDB(projectEntity)
        }
    }

}