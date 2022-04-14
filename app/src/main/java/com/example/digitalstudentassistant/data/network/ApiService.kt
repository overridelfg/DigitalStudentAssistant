package com.example.digitalstudentassistant.data.network

import com.example.digitalstudentassistant.data.models.requests.*
import com.example.digitalstudentassistant.data.models.responses.*
import com.example.digitalstudentassistant.data.models.responses.jwt.TokenResponse
import com.example.digitalstudentassistant.data.models.responses.project.UserProjectResponse
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
    ) : UserProjectResponse

    @POST("api/project/search")
    suspend fun getProjectSearch(
        @Query("key") key : String,
    ) : UserProjectResponse

    @POST("api/project/like/{idProject}")
    suspend fun addLike(
        @Header("Authorization") auth: String,
        @Path("idProject") idProject: String
    ) : ProjectResponse

    @POST("api/project/removeLike/{idProject}")
    suspend fun removeLike(
        @Query("Authorization") token : String,
        @Path("idProject") idProject: String
    ): ProjectResponse

    @POST("api/project/updateProject")
    suspend fun updateProject(
        @Header("Authorization") auth: String,
        @Query("idProject") idProject: String,
        @Body updateProjectRequest: UpdateProjectRequest
    ): ProjectResponse

    @GET("api/project/recommend")
    suspend fun getRecommendationProjects(
        @Query("Token") token : String
    ): MutableList<ProjectResponse>





    @GET("cv/all")
    suspend fun getAllCV() : List<CVResponse>

    @POST("cv/add")
    suspend fun addCV(
        @Header("Authorization") auth: String,
        @Body cv: CVRequest
    ) : CVResponse
}