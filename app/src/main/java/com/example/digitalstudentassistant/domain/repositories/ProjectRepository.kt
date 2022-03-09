package com.example.digitalstudentassistant.domain.repositories

import com.example.digitalstudentassistant.data.database.ProjectEntity
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {

//    suspend fun getProject(id: Int) : Project
//
//    suspend fun createProject()
//
//    suspend fun editProject()
    fun getAllProjectsFromDB() : Flow<List<ProjectEntity>>

    fun getProjectFromDB(projectId: Int) : Flow<ProjectEntity>

    suspend fun addProjectDB(projectEntity: ProjectEntity)

    suspend fun updateProjectDB(projectEntity: ProjectEntity)

    suspend fun deleteProjectDB(projectEntity: ProjectEntity)
}