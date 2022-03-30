package com.example.digitalstudentassistant.data.network

import com.example.digitalstudentassistant.data.UserPrefsStorage
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(private val userPrefsStorage: UserPrefsStorage) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= MAX_ATTEMPTS)
            return null
        return response.request
            .newBuilder()
            .header(AUTH_HEADER, userPrefsStorage.loadUserFromPrefs()?.token.orEmpty())
            .build()
    }

    private fun responseCount(response: Response): Int {
        var result = 1
        var r: Response? = response
        while (true) {
            r = r?.priorResponse ?: return result
            result++
        }
    }

    companion object {
        const val MAX_ATTEMPTS = 3
        const val AUTH_HEADER = "Token"
    }
}