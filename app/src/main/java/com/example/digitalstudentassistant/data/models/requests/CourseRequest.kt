package com.example.digitalstudentassistant.data.models.requests

data class CourseRequest(
    val name: String,
    val spec: String,
    val seatsNumber: Int,
    val teacher: String,
    val source: String,
)