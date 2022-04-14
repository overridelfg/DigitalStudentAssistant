package com.example.digitalstudentassistant.data.models.responses.project

data class Body(
    val communication: String,
    val creatorId: String,
    val description: String,
    val id: String,
    val tags: List<Tag>,
    val title: String
)