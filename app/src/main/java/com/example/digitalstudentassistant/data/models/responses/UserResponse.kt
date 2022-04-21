package com.example.digitalstudentassistant.data.models.responses

data class UserResponse(
    val id: String,
    val email: String,
    val nickname: String,
    val phoneNumber : String,
    val firstName : String,
    val lastName : String,
    val telegram: String
)