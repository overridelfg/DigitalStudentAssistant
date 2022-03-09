package com.example.digitalstudentassistant.data.network

import com.example.digitalstudentassistant.data.models.requests.LoginRequest
import com.example.digitalstudentassistant.data.models.responses.login.LoginResponse
import com.example.digitalstudentassistant.data.models.ProjectResponse
import com.example.digitalstudentassistant.data.models.requests.RegisterRequest
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): LoginResponse

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("")
    suspend fun getProject() : ProjectResponse
}