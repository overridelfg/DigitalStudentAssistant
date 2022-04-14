package com.example.digitalstudentassistant.domain.repositories

import com.example.digitalstudentassistant.data.database.ProjectEntity
import com.example.digitalstudentassistant.data.models.requests.ProjectRequest
import com.example.digitalstudentassistant.data.models.requests.UpdateProjectRequest
import com.example.digitalstudentassistant.data.models.responses.ProjectResponse
import com.example.digitalstudentassistant.data.models.responses.project.UserProjectResponse
import com.example.digitalstudentassistant.domain.OperationResult
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

    suspend fun showAllProjects() : OperationResult<List<ProjectResponse>, String?>

    suspend fun createProject(projectRequest: ProjectRequest): OperationResult<ProjectResponse, String?>

    suspend fun getProjectSearch(key : String) : OperationResult<UserProjectResponse, String?>

    suspend fun addLike(projectId: String) : OperationResult<ProjectResponse, String?>

    suspend fun removeLike(projectId: String) : OperationResult<ProjectResponse, String?>

    suspend fun updateProject(idProject: String, updateProjectRequest: UpdateProjectRequest) : OperationResult<ProjectResponse, String?>

}