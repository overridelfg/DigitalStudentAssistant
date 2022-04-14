package com.example.digitalstudentassistant.domain.repositories

import com.example.digitalstudentassistant.data.models.responses.ProjectResponse
import com.example.digitalstudentassistant.domain.OperationResult

interface ProjectsRepository {
    suspend fun getUserProjects() : OperationResult<List<ProjectResponse>, String?>
}