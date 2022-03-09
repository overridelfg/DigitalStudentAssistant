package com.example.digitalstudentassistant.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectsDao {
    @Query("SELECT * FROM projects_table")
    fun getProjects(): Flow<List<ProjectEntity>>

    @Query("SELECT * FROM projects_table WHERE projectId = :projectId")
    fun getProject(projectId: Int): Flow<ProjectEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProject(projectEntity: ProjectEntity)

    @Update
    suspend fun updateProject(projectEntity: ProjectEntity)

    @Delete
    suspend fun deleteProject(projectEntity: ProjectEntity)
}