package com.example.digitalstudentassistant.data.network

import com.example.digitalstudentassistant.data.models.requests.LoginRequest
import com.example.digitalstudentassistant.data.models.requests.CVRequest
import com.example.digitalstudentassistant.data.models.requests.ProjectRequest
import com.example.digitalstudentassistant.data.models.requests.RegisterRequest
import com.example.digitalstudentassistant.data.models.responses.*
import com.example.digitalstudentassistant.data.models.responses.jwt.TokenResponse
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {

    //auth
    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): LoginResponse

    @POST("auth/authUser")
    suspend fun auth(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("api/account/me")
    suspend fun getUserData(
        @Header("Authorization") auth: String,
    ) : TokenResponse

    //project
    @GET("api/project/all")
    suspend fun getProjects(
    ) : List<ProjectResponse>

    @POST("api/project/add")
    suspend fun createProject(
        @Header("Authorization") auth: String,
        @Body projectRequest: ProjectRequest,
    ) : ProjectResponse

    @GET("api/project/getCreated")
    suspend fun getUserProjects(
        @Header("Authorization") auth: String
    ) : List<ProjectResponse>

    @GET("project/{id}")
    suspend fun getProject(
        @Path("id", encoded = true) id : Int,
        @Query("Token") token : String
    ) : ProjectResponse


    @POST("api/project/search")
    suspend fun getProjectSearch(
        @Query("key") key : String,
    ) : List<ProjectResponse>

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

    @GET("api/project/recommend")
    suspend fun getRecommendationProjects(
        @Query("Token") token : String
    ): MutableList<ProjectResponse>

    @POST("project/like/{idProject}")
    suspend fun addLike(
        @Query("Token") token : String,
        @Path("projectId") idProject: String
    )

    @POST("api/project/removeLike/{idProject}")
    suspend fun removeLike(
        @Query("Token") token : String,
        @Path("idProject") idProject: String
    )

    @GET("cv/all")
    suspend fun getAllCV() : List<CVResponse>

    @POST("cv/add")
    suspend fun addCV(
        @Query("Token") token : String,
        @Query("cv") cv: CVRequest
    ) : CVResponse
}