package com.example.digitalstudentassistant.data.repositories

import com.example.digitalstudentassistant.data.database.ProjectsDao
import com.example.digitalstudentassistant.data.database.ProjectEntity
import com.example.digitalstudentassistant.domain.repositories.ProjectRepository
import kotlinx.coroutines.flow.Flow

class ProjectRepositoryImpl(private val projectsDao: ProjectsDao): ProjectRepository {
    override fun getAllProjectsFromDB(): Flow<List<ProjectEntity>> {
        return projectsDao.getProjects()
    }

    override fun getProjectFromDB(projectId: Int): Flow<ProjectEntity> {
        return projectsDao.getProject(projectId)
    }

    override suspend fun addProjectDB(projectEntity: ProjectEntity) {
        projectsDao.addProject(projectEntity)
    }

    override suspend fun updateProjectDB(projectEntity: ProjectEntity) {
        projectsDao.updateProject(projectEntity)
    }

    override suspend fun deleteProjectDB(projectEntity: ProjectEntity) {
        projectsDao.deleteProject(projectEntity)
    }

}