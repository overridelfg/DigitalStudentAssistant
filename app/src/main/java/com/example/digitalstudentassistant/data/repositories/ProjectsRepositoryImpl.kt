package com.example.digitalstudentassistant.data.repositories

import android.content.Context
import com.example.digitalstudentassistant.data.UserPrefsStorage
import com.example.digitalstudentassistant.data.models.responses.ProjectResponse
import com.example.digitalstudentassistant.data.models.responses.project.UserProjectResponse
import com.example.digitalstudentassistant.data.network.ApiProvider
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.repositories.ProjectsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectsRepositoryImpl(private val context: Context) : ProjectsRepository {

    private val apiService =  ApiProvider(context).apiService
    private val userPrefsStorage = UserPrefsStorage(context)
    override suspend fun getUserProjects(): OperationResult<UserProjectResponse, String?> {
        return withContext(Dispatchers.IO){
            try {
                val result = apiService.getUserProjects("Bearer ${userPrefsStorage.loadUserFromPrefs()?.token.orEmpty()}")
                return@withContext OperationResult.Success(result)
            }catch (e: Throwable){
                return@withContext OperationResult.Error(e.message)
            }
        }
    }
}