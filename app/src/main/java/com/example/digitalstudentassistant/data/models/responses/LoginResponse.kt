package com.example.digitalstudentassistant.data.models.responses

import com.example.digitalstudentassistant.domain.models.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val access_token: String,
    val error: Any,
    val error_description: Any,
    val error_uri: Any,
    val expires_in: Int,
    val id_token: Any,
    @SerializedName("not-before-policy")
    val nonBeforePolicy: Int,
    val refresh_expires_in: Int,
    val refresh_token: String,
    val scope: String,
    val session_state: String,
    val token_type: String
)

//fun LoginResponse.toUser(): User {
//
//    return User(
//        id = userResponse.id,
//        email = userResponse.email,
//        nickname = userResponse.nickname,
//        phoneNumber = userResponse.phoneNumber,
//        firstname = userResponse.firstname,
//        lastname = userResponse.lastname,
//        password = userResponse.password,
//        telegram = userResponse.telegram,
//        token = token
//    )
//}