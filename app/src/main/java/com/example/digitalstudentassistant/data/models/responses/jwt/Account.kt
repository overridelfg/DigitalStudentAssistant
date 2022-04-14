package com.example.digitalstudentassistant.data.models.responses.jwt

data class Account(
    val roles: List<String>,
    val verify_caller: Any?
)