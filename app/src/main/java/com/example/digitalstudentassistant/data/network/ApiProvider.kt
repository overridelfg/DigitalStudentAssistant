package com.example.digitalstudentassistant.data.network

import android.content.Context
import com.example.digitalstudentassistant.data.UserPrefsStorage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiProvider(private val context: Context) {
    private val BASE_URL = "http://62.84.123.230:443/"

    private fun getRetrofit(): Retrofit {
        val userPrefsStorage : UserPrefsStorage = UserPrefsStorage(context)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)


        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}