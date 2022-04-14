package com.example.digitalstudentassistant.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects_table")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "projectId")
    val id: Int,
    @ColumnInfo(name = "projectOwnerId")
    val projectOwnerId: Int,
    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "communication")
    val communication: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "status")
    val tags: String,
){
    constructor(projectOwnerId: Int,
                    name : String,
                    communication: String,
                    description: String,
                    tags: String) : this(0, projectOwnerId, name, communication, description, tags)
}