package com.example.digitalstudentassistant.domain.repositories

import com.example.digitalstudentassistant.data.models.responses.CourseResponse
import com.example.digitalstudentassistant.domain.OperationResult

interface CoursesRepository {
    suspend fun getAllCourses() : OperationResult<List<CourseResponse>, String?>
}