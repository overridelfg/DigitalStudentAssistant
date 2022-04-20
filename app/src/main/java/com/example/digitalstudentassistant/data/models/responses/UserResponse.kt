package com.example.digitalstudentassistant.data.models.responses

import com.example.digitalstudentassistant.domain.models.User

data class UserResponse(
    val id: String,
    val email: String,
    val nickname: String,
    val phoneNumber : String,
    val firstName : String,
    val lastName : String,
    val telegram: String
)