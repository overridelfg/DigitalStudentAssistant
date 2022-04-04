package com.example.digitalstudentassistant.domain.models

data class User(
    val id: Long,
    val email: String,
    val nickname: String,
    val phoneNumber : String,
    val firstname : String,
    val lastname : String,
    val surname : String?,
    val interests : String,
    val telegram: String,
    val password: String,
    val token : String
)