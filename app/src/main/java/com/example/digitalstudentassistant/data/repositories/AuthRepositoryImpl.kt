package com.example.digitalstudentassistant.data.repositories

import com.example.digitalstudentassistant.data.UserPrefsStorage
import com.example.digitalstudentassistant.data.models.requests.LoginRequest
import com.example.digitalstudentassistant.data.models.requests.RegisterRequest
import com.example.digitalstudentassistant.data.models.responses.login.LoginResponse
import com.example.digitalstudentassistant.data.models.responses.login.toUser
import com.example.digitalstudentassistant.data.network.ApiService
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.models.User
import com.example.digitalstudentassistant.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val userPrefsStorage: UserPrefsStorage) : AuthRepository{
    override fun loadUserFromPrefs(): User? {
        return userPrefsStorage.loadUserFromPrefs()
    }

    override fun observeUser(): Flow<User?> {
        return userPrefsStorage.observeUser()
    }

    override fun saveUserToPrefs(user: User?) {
        userPrefsStorage.saveUserToPrefs(user)
    }

    override suspend fun login(email: String, password: String) : OperationResult<LoginResponse, String?> {
        return withContext(Dispatchers.IO){
            try {
                val result = apiService.login(LoginRequest(email, password))
                userPrefsStorage.saveUserToPrefs(result.toUser())
                return@withContext OperationResult.Success(result)
            }catch (e: Throwable){
                return@withContext OperationResult.Error(e.message)
            }
        }
    }

    override suspend fun register(email: String,
                                  nickname: String,
                                  phoneNumber : String,
                                  firstname: String,
                                  lastname : String,
                                  surname : String,
                                  telegram : String,
                                  password: String,
    ) : OperationResult<LoginResponse, String?> {
        return withContext(Dispatchers.IO){
            try {
                val result = apiService.register(RegisterRequest( email,
                    nickname = nickname,
                    phoneNumber = phoneNumber,
                    firstname = firstname,
                    lastname = lastname,
                    surname = surname,
                    telegram = telegram,
                    password = password))
                userPrefsStorage.saveUserToPrefs(result.toUser())
                return@withContext OperationResult.Success(result)
            }catch (e: Throwable){
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