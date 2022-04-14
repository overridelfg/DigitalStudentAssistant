package com.example.digitalstudentassistant.data.models.responses

import com.example.digitalstudentassistant.domain.models.User

data class UserResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val phoneNumber : String,
    val firstname : String,
    val lastname : String,
    val surname : String?,
    val interests : List<String>,
    val likedProjects : Set<String>,
    val telegram: String,
    val password: String,
)