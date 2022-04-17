package com.example.digitalstudentassistant.data.repositories

import android.content.Context
import com.example.digitalstudentassistant.data.models.responses.CourseResponse
import com.example.digitalstudentassistant.data.models.responses.toCV
import com.example.digitalstudentassistant.data.network.ApiProvider
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.repositories.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoursesRepositoryImpl(context: Context) : CoursesRepository {
    private val apiService =  ApiProvider(context).apiService
    override suspend fun getAllCourses(): OperationResult<List<CourseResponse>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.getAllCourses()
                return@withContext OperationResult.Success(result)
            } catch (e: Throwable) {
                return@withContext OperationResult.Error(e.message)
            }
        }
    }
}