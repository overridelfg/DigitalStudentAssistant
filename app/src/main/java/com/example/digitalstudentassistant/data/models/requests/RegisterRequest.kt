    package com.example.digitalstudentassistant.data.models.requests

data class RegisterRequest(
    val email: String,
    val nickname: String,
    val phoneNumber : String,
    val firstName : String,
    val lastName : String,
    val telegram: String,
    val password: String,
)