package com.example.digitalstudentassistant.data.models.responses

data class CourseResponse(
    val name: String,
    val spec: String,
    val seatsNumber: Int,
    val teacher: String,
    val source: String,
)