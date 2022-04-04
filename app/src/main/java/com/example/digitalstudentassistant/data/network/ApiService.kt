package com.example.digitalstudentassistant.data.network

import com.example.digitalstudentassistant.data.models.requests.LoginRequest
import com.example.digitalstudentassistant.data.models.responses.login.LoginResponse
import com.example.digitalstudentassistant.data.models.ProjectResponse
import com.example.digitalstudentassistant.data.models.UserResponse
import com.example.digitalstudentassistant.data.models.requests.RegisterRequest
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): LoginResponse

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("project/projects")
    suspend fun getProjects(
        @Query("Token") token : String
    ) : List<ProjectResponse>

    @GET("project/{id}")
    suspend fun getProject(
        @Path("id", encoded = true) id : Int,
        @Query("Token") token : String
    ) : ProjectResponse

    @GET("project/search")
    suspend fun getProjectSearch(
        @Query("project") projectName : String,
        @Query("Token") token : String
    ) : List<ProjectResponse>

    @POST("project/create")
    suspend fun createProject(
        @Path("p") projectResponse: ProjectResponse
    ) : ResponseBody

    @POST("project/update")
    suspend fun updateProject(
        @Query("id", encoded = true) id : Int
    ): ResponseBody

    @DELETE("project/delete")
    suspend fun deleteProject(
        @Query("id") id: String
    ): ResponseBody

    @POST("project/enroll")
    suspend fun enrollProject(
        @Query("projectId") id : Int,
        @Query("user") user : UserResponse
    ): ResponseBody

    @GET("project/recommendation")
    suspend fun getRecommendationProjects(

    ): List<ProjectResponse>

    @POST("project/likes")
    suspend fun addLike(
         @Query("id") id: String
    )
}