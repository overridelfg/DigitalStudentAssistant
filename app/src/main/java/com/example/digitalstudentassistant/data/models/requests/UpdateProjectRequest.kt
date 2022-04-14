package com.example.digitalstudentassistant.data.models.requests

data class UpdateProjectRequest(
    val communication: String,
    val description: String,
    val tags: List<Tag>,
    val title: String
)