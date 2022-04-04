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
        val id = sharedPreferences.getLong(ID_KEY, -1)
        val email = sharedPreferences.getString(EMAIL_KEY, null)
        val nickname = sharedPreferences.getString(NICKNAME_KEY, null)
        val phoneNumber = sharedPreferences.getString(PHONE_NUMBER_KEY, null)
        val firstname = sharedPreferences.getString(FIRSTNAME_KEY, null)
        val lastname = sharedPreferences.getString(LASTNAME_KEY, null)
        val surname = sharedPreferences.getString(SURNAME_KEY, null)
        val password = sharedPreferences.getString(PASSWORD_KEY, null)
        val telegram = sharedPreferences.getString(TELEGRAM_KEY, null)
        val token = sharedPreferences.getString(TOKEN_KEY, null)
        if(id != -1L && !email.isNullOrEmpty()
            && !nickname.isNullOrEmpty()
            && !phoneNumber.isNullOrEmpty()
            && !firstname.isNullOrEmpty()
            && !lastname.isNullOrEmpty()
            && !surname.isNullOrEmpty()
            && !password.isNullOrEmpty()
            && !telegram.isNullOrEmpty()
            && !token.isNullOrEmpty()){
            return User(
                id = id,
                email = email,
                nickname = nickname,
                phoneNumber = phoneNumber,
                firstname = firstname,
                lastname = lastname,
                surname = surname,
                password = password,
                telegram = telegram,
                token = token
                )
            }
        return null
    }

    @SuppressLint("CommitPrefEdits")
    fun saveUserToPrefs(user : User?){
        if(user != null){
            sharedPreferences.edit()
                .putLong(ID_KEY, user.id)
                .putString(EMAIL_KEY, user.email)
                .putString(NICKNAME_KEY, user.nickname)
                .putString(PHONE_NUMBER_KEY, user.phoneNumber)
                .putString(FIRSTNAME_KEY, user.firstname)
                .putString(LASTNAME_KEY, user.lastname)
                .putString(SURNAME_KEY, user.surname)
                .putString(PASSWORD_KEY, user.password)
                .putString(TELEGRAM_KEY, user.telegram)
                .putString(TOKEN_KEY, user.token)
                .apply()
        }else{
            sharedPreferences.edit()
                .remove(ID_KEY)
                .remove(EMAIL_KEY)
                .remove(NICKNAME_KEY)
                .remove(PHONE_NUMBER_KEY)
                .remove(FIRSTNAME_KEY)
                .remove(LASTNAME_KEY)
                .remove(SURNAME_KEY)
                .remove(PASSWORD_KEY)
                .remove(TELEGRAM_KEY)
                .remove(TOKEN_KEY)
                .apply()
        }
        userStateFlow.value = user
    }
    companion object {
        private const val ID_KEY = "id"
        private const val EMAIL_KEY = "email"
        private const val NICKNAME_KEY = "nickname"
        private const val PHONE_NUMBER_KEY = "phoneNumber"
        private const val FIRSTNAME_KEY = "firstname"
        private const val LASTNAME_KEY = "lastname"
        private const val SURNAME_KEY = "surname"
        private const val INTERESTS_KEY = "interests"
        private const val PASSWORD_KEY = "password"
        private const val TELEGRAM_KEY = "telegram"
        private const val TOKEN_KEY = "token"
    }
}