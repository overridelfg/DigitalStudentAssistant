package com.example.digitalstudentassistant.data.models

data class ProjectResponse(
    val id: Int,
    val name : String,
    val purpose: String,
    val description: String,
    val deadlineDate: String,
    val participantsNumber: Int,
    val recordingPeriod : String,
    val status: String,
)