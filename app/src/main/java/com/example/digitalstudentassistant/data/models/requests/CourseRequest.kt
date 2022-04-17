package com.example.digitalstudentassistant.data.models.requests

data class CourseRequest(
    val id: String? = null,
    val name: String,
    val about: String,
    val source: String,
)