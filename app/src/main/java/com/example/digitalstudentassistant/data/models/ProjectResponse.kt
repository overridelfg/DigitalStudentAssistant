package com.example.digitalstudentassistant.data.models

data class ProjectResponse(
    val id: Int,
    val userId : Int,
    val name : String,
    val purpose: String,
    val description: String,
    val deadlineProjectFromDate: String,
    val deadlineProjectToDate: String,
    val deadlineTeamFromDate: String,
    val deadlineTeamToDate: String,
    val participantsNumber: Int,
    val recordingPeriod : String,
    val status: String,
    val likeCount : Int
)