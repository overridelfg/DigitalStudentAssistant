package com.example.digitalstudentassistant.data.repositories

import android.content.Context
import com.example.digitalstudentassistant.data.UserPrefsStorage
import com.example.digitalstudentassistant.data.models.requests.LoginRequest
import com.example.digitalstudentassistant.data.models.requests.RegisterRequest
import com.example.digitalstudentassistant.data.models.responses.LoginResponse
import com.example.digitalstudentassistant.data.network.ApiProvider
import com.example.digitalstudentassistant.data.network.ApiService
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.models.User
import com.example.digitalstudentassistant.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(context: Context) : AuthRepository {

    private val apiService = ApiProvider(context).apiService
    private val userPrefsStorage = UserPrefsStorage(context)
    override fun loadUserFromPrefs(): User? {
        return userPrefsStorage.loadUserFromPrefs()
    }

    override fun saveUserToPrefs(user: User?) {
        userPrefsStorage.saveUserToPrefs(user)
    }

    override suspend fun login(
        username: String,
        password: String
    ): OperationResult<LoginResponse, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.auth(LoginRequest(username, password))

                userPrefsStorage.saveUserToPrefs(
                    User(
                        id = "sda",
                        email = "userInfo.email!!",
                        nickname = username,
                        firstname = "userInfo.name",
                        token = result.access_token
                    )
                )

                return@withContext OperationResult.Success(result)
            } catch (e: Throwable) {
                return@withContext OperationResult.Error(e.message)
            }
        }
    }

    override suspend fun register(
        email: String,
        nickname: String,
        phoneNumber: String,
        firstname: String,
        lastname: String,
        telegram: String,
        password: String,
    ): OperationResult<LoginResponse, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.register(
                    RegisterRequest(
                        email,
                        nickname = nickname,
                        phoneNumber = phoneNumber,
                        firstName = firstname,
                        lastName = lastname,
                        telegram = telegram,
                        password = password
                    )
                )
                userPrefsStorage.saveUserToPrefs(
                    User(
                        id = "userInfo.sub",
                        email = email,
                        nickname = nickname,
                        firstname = "userInfo.name",
                        token = result.access_token
                    )
                )
                return@withContext OperationResult.Success(result)
            } catch (e: Throwable) {
                return@withContext OperationResult.Error(e.message)
            }
        }
    }

    override fun logOut() {
        saveUserToPrefs(null)
    }

    override fun saveUser(user: User) {
        userPrefsStorage.saveUserToPrefs(user)
    }
}