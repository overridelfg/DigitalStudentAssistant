package com.example.digitalstudentassistant.data.repositories

import com.example.digitalstudentassistant.data.models.requests.LoginRequest
import com.example.digitalstudentassistant.data.models.requests.RegisterRequest
import com.example.digitalstudentassistant.data.models.responses.login.LoginResponse
import com.example.digitalstudentassistant.data.network.ApiService
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(private val apiService: ApiService) : AuthRepository{
    override suspend fun login(email: String, password: String) : OperationResult<LoginResponse, String?> {
        return withContext(Dispatchers.IO){
            try {
                val result = apiService.login(LoginRequest(email, password))
                return@withContext OperationResult.Success(result)
            }catch (e: Throwable){
                return@withContext OperationResult.Error(e.message)
            }
        }
    }

    override suspend fun register(email: String, nickname: String, password: String) : OperationResult<LoginResponse, String?> {
        return withContext(Dispatchers.IO){
            try {
                val result = apiService.register(RegisterRequest( email, nickname, password))
                return@withContext OperationResult.Success(result)
            }catch (e: Throwable){
                return@withContext OperationResult.Error(e.message)
            }
        }
    }
}