package com.example.digitalstudentassistant.data.models.responses.login

import com.example.digitalstudentassistant.data.models.UserProfile
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val userProfile: UserProfile,
    @SerializedName("accessToken")
    val access_token: String,
)