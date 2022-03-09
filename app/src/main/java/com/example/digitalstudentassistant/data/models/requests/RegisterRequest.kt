    package com.example.digitalstudentassistant.data.models.requests

data class RegisterRequest(
    val email: String,
    val nickname: String,
    val password: String
)