package com.example.digitalstudentassistant.data.models.responses

data class TagResponse(
    val id: String,
    val name: String,
    val about: String? = null
)