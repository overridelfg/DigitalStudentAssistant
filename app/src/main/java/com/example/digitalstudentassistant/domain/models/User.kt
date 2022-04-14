package com.example.digitalstudentassistant.domain.models

data class User(
    val id: String,
    val email: String,
    val nickname: String,
    val firstname : String,
    val token : String
)