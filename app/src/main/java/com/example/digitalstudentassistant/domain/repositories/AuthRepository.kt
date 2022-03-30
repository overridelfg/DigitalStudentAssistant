package com.example.digitalstudentassistant.domain.repositories

import com.example.digitalstudentassistant.data.models.responses.login.LoginResponse
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.models.User
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface AuthRepository {

    fun loadUserFromPrefs() : User?

    fun observeUser() : Flow<User?>

    fun saveUserToPrefs(user: User?)

    suspend fun login(email: String, password: String) : OperationResult<LoginResponse, String?>

    suspend fun register(email: String,
                         nickname: String,
                         phoneNumber : String,
                         firstname: String,
                         lastname : String,
                         surname : String,
                         telegram : String,
                         password: String): OperationResult<LoginResponse, String?>

    fun logOut()

    fun saveUser(user: User)
}