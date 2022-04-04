package com.example.digitalstudentassistant.data.models.responses.login

import com.example.digitalstudentassistant.data.models.UserResponse
import com.example.digitalstudentassistant.domain.models.User

data class LoginResponse(
    val userResponse: UserResponse,
    val token: String,
)

fun LoginResponse.toUser(): User {
    var interests = ""
    for(i in userResponse.interests){
        interests += "$i:"
    }
    return User(
        id = userResponse.id,
        email = userResponse.email,
        nickname = userResponse.nickname,
        phoneNumber = userResponse.phoneNumber,
        firstname = userResponse.firstname,
        lastname = userResponse.lastname,
        surname = userResponse.surname,
        password = userResponse.password,
        telegram = userResponse.telegram,
        interests = interests,
        token = token
    )
}