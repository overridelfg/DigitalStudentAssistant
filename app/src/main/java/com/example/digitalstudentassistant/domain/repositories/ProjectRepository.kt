package com.example.digitalstudentassistant.domain.repositories

import com.example.digitalstudentassistant.data.models.requests.ProjectRequest
import com.example.digitalstudentassistant.data.models.requests.UpdateProjectRequest
import com.example.digitalstudentassistant.data.models.responses.Likes
import com.example.digitalstudentassistant.data.models.responses.ProjectResponse
import com.example.digitalstudentassistant.data.models.responses.UserResponse
import com.example.digitalstudentassistant.data.models.responses.Views
import com.example.digitalstudentassistant.domain.OperationResult
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    suspend fun showAllProjects() : OperationResult<List<ProjectResponse>, String?>

    suspend fun getSortProjects() : OperationResult<List<ProjectResponse>, String?>

    suspend fun createProject(projectRequest: ProjectRequest): OperationResult<ProjectResponse, String?>

    suspend fun getProjectSearch(key : String) : OperationResult<List<ProjectResponse>, String?>

    suspend fun addLike(projectId: String) : OperationResult<ProjectResponse, String?>

    suspend fun removeLike(projectId: String) : OperationResult<ProjectResponse, String?>

    suspend fun showLikes(projectId: String) : OperationResult<Likes, String?>

    suspend fun updateProject(idProject: String, updateProjectRequest: UpdateProjectRequest) : OperationResult<ProjectResponse, String?>

    suspend fun postView(idProject: String) : OperationResult<ProjectResponse, String?>

    suspend fun getViews(idProject: String) : OperationResult<Views, String?>

    suspend fun getWhoLiked(idProject: String) : OperationResult<List<UserResponse>, String?>
}