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
    ) : List<ProjectResponse>

    @GET("api/project/getLiked")
    suspend fun getLiked(
        @Header("Authorization") auth: String
    ) : List<ProjectResponse>

    @POST("api/project/search")
    suspend fun getProjectSearch(
        @Query("key") key : String,
    ) : List<ProjectResponse>

    @POST("api/project/like/{idProject}")
    suspend fun addLike(
        @Header("Authorization") auth: String,
        @Path("idProject") idProject: String
    ) : ProjectResponse

    @POST("api/project/removeLike/{idProject}")
    suspend fun removeLike(
        @Header("Authorization") token : String,
        @Path("idProject") idProject: String
    ): ProjectResponse

    @GET("api/project/showLikes")
    suspend fun showLikes(
        @Query("idProject") idProject: String
    ) : Likes

    @POST("api/project/updateProject")
    suspend fun updateProject(
        @Header("Authorization") auth: String,
        @Query("idProject") idProject: String,
        @Body updateProjectRequest: UpdateProjectRequest
    ): ProjectResponse

    @GET("api/project/recommend")
    suspend fun getRecommendationProjects(
        @Header("Authorization") auth: String
    ): List<ProjectResponse>

    @POST("api/project/view/{idProject}")
    suspend fun postView(
        @Header("Authorization") auth: String,
        @Path("idProject") idProject: String
    ) : ProjectResponse

    @GET("api/project/showViews")
    suspend fun getViews(
        @Header("Authorization") auth: String,
        @Query("idProject") idProject: String
    ) : Views

    @GET("api/project/sortByLikes")
    suspend fun getSortProjects() : List<ProjectResponse>

    @GET("api/project/getWhoLiked")
    suspend fun getWhoLiked(
        @Header("Authorization") auth: String,
        @Query("idProject") idProject: String
    ) : List<UserResponse>


    @GET("api/cv/all")
    suspend fun getAllCV() : List<CVResponse>

    @POST("api/cv/add")
    suspend fun addCV(
        @Header("Authorization") auth: String,
        @Body cv: CVRequest
    ) : CVResponse

    @POST("api/cv/update")
    suspend fun updateCV(
        @Header("Authorization") auth: String,
        @Query("idCV") idCV : String,
        @Body cv: CVRequest
    ): CVResponse

    @GET("api/cv/getCV/{idUser}")
    suspend fun getUserCV(
        @Path("idUser") idUser: String
    ) : List<CVResponse>

    @GET("api/courses/all")
    suspend fun getAllCourses() : List<CourseResponse>
}