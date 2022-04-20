package com.example.digitalstudentassistant.data.repositories

import android.content.Context
import com.example.digitalstudentassistant.data.UserPrefsStorage
import com.example.digitalstudentassistant.data.database.ProjectsDao
import com.example.digitalstudentassistant.data.database.ProjectEntity
import com.example.digitalstudentassistant.data.models.requests.CVRequest
import com.example.digitalstudentassistant.data.models.requests.ProjectRequest
import com.example.digitalstudentassistant.data.models.requests.UpdateProjectRequest
import com.example.digitalstudentassistant.data.models.responses.*
import com.example.digitalstudentassistant.data.models.responses.project.UserProjectResponse
import com.example.digitalstudentassistant.data.network.ApiProvider
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.repositories.ProjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ProjectRepositoryImpl(private val projectsDao: ProjectsDao, private val context: Context): ProjectRepository {
    private val apiService =  ApiProvider(context).apiService
    private val userPrefsStorage = UserPrefsStorage(context)
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

    override suspend fun showAllProjects(): OperationResult<List<ProjectResponse>, String?> {
        return withContext(Dispatchers.IO){
            try {
                val result = apiService.getProjects()
                return@withContext OperationResult.Success(result)
            }catch (e: Throwable){
                return@withContext OperationResult.Error(e.message)
            }
        }
    }

    override suspend fun getSortProjects(): OperationResult<List<ProjectResponse>, String?> {
        return withContext(Dispatchers.IO){
            try {
                val result = apiService.getSortProjects()
                return@withContext OperationResult.Success(result)
            }catch (e: Throwable){
                return@withContext OperationResult.Error(e.message)
            }
        }
    }

    override suspend fun createProject(projectRequest: ProjectRequest) : OperationResult<ProjectResponse, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.createProject("Bearer ${userPrefsStorage.loadUserFromPrefs()?.token.orEmpty()}",projectRequest)
                return@withContext OperationResult.Success(result)
            } catch (e: Throwable) {
                return@withContext OperationResult.Error(e.message)
            }
        }
    }

    override suspend fun getProjectSearch(key: String): OperationResult<List<ProjectResponse>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.getProjectSearch(key)
                return@withContext OperationResult.Success(result)
            } catch (e: Throwable) {
                return@withContext OperationResult.Error(e.message)
            }
        }
    }

    override suspend fun addLike(projectId: String): OperationResult<ProjectResponse, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.addLike(
                    "Bearer ${userPrefsStorage.loadUserFromPrefs()?.token.orEmpty()}",
                    projectId
                )
                return@withContext OperationResult.Success(result)
            } catch (e: Throwable) {
                return@withContext OperationResult.Error(e.message)
            }
        }
    }

    override suspend fun removeLike(projectId: String): OperationResult<ProjectResponse, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.removeLike(
                    "Bearer ${userPrefsStorage.loadUserFromPrefs()?.token.orEmpty()}",
                    projectId
                )
                return@withContext OperationResult.Success(result)
            } catch (e: Throwable) {
                return@withContext OperationResult.Error(e.message)
            }
        }
    }

    override suspend fun showLikes(projectId: String): OperationResult<Likes, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.showLikes(projectId)
                return@withContext OperationResult.Success(result)
            } catch (e: Throwable) {
                return@withContext OperationResult.Error(e.message)
            }
        }
    }

    override suspend fun updateProject(
        idProject: String,
        updateProjectRequest: UpdateProjectRequest
    ): OperationResult<ProjectResponse, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.updateProject(
                    "Bearer ${userPrefsStorage.loadUserFromPrefs()?.token.orEmpty()}",
                    idProject, updateProjectRequest
                )
                return@withContext OperationResult.Success(result)
            } catch (e: Throwable) {
                return@withContext OperationResult.Error(e.message)
            }
        }
    }

    override suspend fun postView(idProject: String): OperationResult<ProjectResponse, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.postView(
                    "Bearer ${userPrefsStorage.loadUserFromPrefs()?.token.orEmpty()}",
                    idProject
                )
                return@withContext OperationResult.Success(result)
            } catch (e: Throwable) {
                return@withContext OperationResult.Error(e.message)
            }
        }
    }

    override suspend fun getViews(idProject: String): OperationResult<Views, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.getViews(
                    "Bearer ${userPrefsStorage.loadUserFromPrefs()?.token.orEmpty()}",
                    idProject
                )
                return@withContext OperationResult.Success(result)
            } catch (e: Throwable) {
                return@withContext OperationResult.Error(e.message)
            }
        }
    }

    override suspend fun getWhoLiked(idProject: String): OperationResult<List<UserResponse>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.getWhoLiked(
                    "Bearer ${userPrefsStorage.loadUserFromPrefs()?.token.orEmpty()}",
                    idProject)
                return@withContext OperationResult.Success(result)
            } catch (e: Throwable) {
                return@withContext OperationResult.Error(e.message)
            }
        }
    }


}