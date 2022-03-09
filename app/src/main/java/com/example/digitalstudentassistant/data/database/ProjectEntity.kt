package com.example.digitalstudentassistant.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects_table")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "projectId")
    val id: Int,
    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "purpose")
    val purpose: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "deadlineDate")
    val deadlineDate: String,
    @ColumnInfo(name = "participantsNumber")
    val participantsNumber: Int,
    @ColumnInfo(name = "recordingPeriod")
    val recordingPeriod : String,
    @ColumnInfo(name = "status")
    val status: String,
){
    constructor(name : String,
                    purpose: String,
                    description: String,
                    deadlineDate: String,
                    participantsNumber: Int,
                    recordingPeriod : String,
                    status: String) : this(0, name, purpose, description, deadlineDate, participantsNumber, recordingPeriod, status)
}