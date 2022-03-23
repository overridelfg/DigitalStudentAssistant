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
    @ColumnInfo(name = "purpose")
    val purpose: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "deadlineProjectDateFrom")
    val deadlineProjectDateFrom: String,
    @ColumnInfo(name = "deadlineProjectDateTo: String")
    val deadlineProjectDateTo: String,
    @ColumnInfo(name = "deadlineTeamDateFrom")
    val deadlineTeamDateFrom : String,
    @ColumnInfo(name = "deadlineProjectDateTo")
    val deadlineTeamDateTo : String,
    @ColumnInfo(name = "participantsNumber")
    val participantsNumber: Int,
    @ColumnInfo(name = "status")
    val status: String,
){
    constructor(projectOwnerId: Int,
                    name : String,
                    purpose: String,
                    description: String,
                    deadlineProjectDateFrom: String,
                    deadlineProjectDateTo: String,
                    deadlineTeamDateFrom: String,
                    deadlineTeamDateTo: String,
                    participantsNumber: Int,
                    status: String) : this(0, projectOwnerId, name, purpose, description, deadlineProjectDateFrom, deadlineProjectDateTo, deadlineTeamDateFrom, deadlineTeamDateTo, participantsNumber, status)
}