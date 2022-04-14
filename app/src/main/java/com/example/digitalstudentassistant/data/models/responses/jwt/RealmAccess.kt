package com.example.digitalstudentassistant.data.models.responses.jwt

data class RealmAccess(
    val roles: List<String>,
    val verify_caller: Any?
)