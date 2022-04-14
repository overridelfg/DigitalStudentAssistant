package com.example.digitalstudentassistant.data

import android.annotation.SuppressLint
import android.content.Context
import com.example.digitalstudentassistant.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserPrefsStorage(
    context : Context
) {
    private val sharedPreferences = context.getSharedPreferences(
        "UserPrefs",
        Context.MODE_PRIVATE
    )

    private val userStateFlow = MutableStateFlow(loadUserFromPrefs())

    fun observeUser() : Flow<User?> = userStateFlow

    fun loadUserFromPrefs() : User?{
        val id = sharedPreferences.getString(ID_KEY, "")
        val email = sharedPreferences.getString(EMAIL_KEY, null)
        val nickname = sharedPreferences.getString(NICKNAME_KEY, null)
        val firstname = sharedPreferences.getString(FIRSTNAME_KEY, null)
        val token = sharedPreferences.getString(TOKEN_KEY, null)
        if(!id.isNullOrEmpty() && !email.isNullOrEmpty()
            && !nickname.isNullOrEmpty()
            && !firstname.isNullOrEmpty()
            && !token.isNullOrEmpty()){
            return User(
                id = id,
                email = email,
                nickname = nickname,
                firstname = firstname,
                token = token
                )
            }
        return null
    }

    @SuppressLint("CommitPrefEdits")
    fun saveUserToPrefs(user : User?){
        if(user != null){
            sharedPreferences.edit()
                .putString(ID_KEY, user.id)
                .putString(EMAIL_KEY, user.email)
                .putString(NICKNAME_KEY, user.nickname)
                .putString(FIRSTNAME_KEY, user.firstname)
                .putString(TOKEN_KEY, user.token)
                .apply()
        }else{
            sharedPreferences.edit()
                .remove(ID_KEY)
                .remove(EMAIL_KEY)
                .remove(NICKNAME_KEY)
                .remove(FIRSTNAME_KEY)
                .remove(TOKEN_KEY)
                .apply()
        }
        userStateFlow.value = user
    }
    companion object {
        private const val ID_KEY = "userId"
        private const val EMAIL_KEY = "email"
        private const val NICKNAME_KEY = "nickname"
        private const val PHONE_NUMBER_KEY = "phoneNumber"
        private const val FIRSTNAME_KEY = "firstname"
        private const val LASTNAME_KEY = "lastname"
        private const val PASSWORD_KEY = "password"
        private const val TELEGRAM_KEY = "telegram"
        private const val TOKEN_KEY = "token"
    }
}