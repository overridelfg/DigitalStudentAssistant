package com.example.digitalstudentassistant.domain.repositories

interface ProjectsRepository {

    suspend fun getProjects()
}