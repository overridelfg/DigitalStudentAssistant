package com.example.digitalstudentassistant.ui.projectdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.digitalstudentassistant.data.database.ProjectEntity
import com.example.digitalstudentassistant.data.database.ProjectsDatabase
import com.example.digitalstudentassistant.data.repositories.ProjectRepositoryImpl
import com.example.digitalstudentassistant.domain.repositories.ProjectRepository
import kotlinx.coroutines.flow.Flow


class ProjectDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val projectRepository: ProjectRepository


    init {
        val projectsDao = ProjectsDatabase.create(application).projectsDao()
        projectRepository = ProjectRepositoryImpl(projectsDao)
    }

    fun getProjectFromDB(projectId: Int) : Flow<ProjectEntity> {
        return projectRepository.getProjectFromDB(projectId)
    }
}