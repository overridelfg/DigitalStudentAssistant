package com.example.digitalstudentassistant.domain.models

data class User(
    val id: Int,
    val email: String,
    val nickname: String,
    val password: String,
)