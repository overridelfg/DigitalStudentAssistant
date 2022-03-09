package com.example.digitalstudentassistant.data.models

data class UserProfile(
    val id: Long,
    val email: String,
    val password: String,
    val phone : String
)