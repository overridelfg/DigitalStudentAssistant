package com.example.digitalstudentassistant.data.models.requests

data class ProjectRequest(
    val title: String,
    val description: String,
    val communication: String,
    var creatorId: String,
    val tags: List<TagRequest>
)

