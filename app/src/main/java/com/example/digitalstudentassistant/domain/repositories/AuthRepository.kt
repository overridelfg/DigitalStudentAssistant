package com.example.digitalstudentassistant.domain.repositories

import com.example.digitalstudentassistant.data.models.responses.login.LoginResponse
import com.example.digitalstudentassistant.domain.OperationResult
import okhttp3.ResponseBody

interface AuthRepository {

    suspend fun login(email: String, password: String) : OperationResult<LoginResponse, String?>

    suspend fun register(email: String, nickname: String, password: String): OperationResult<LoginResponse, String?>
}