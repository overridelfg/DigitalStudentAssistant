package com.example.digitalstudentassistant.data.repositories

import android.content.Context
import com.example.digitalstudentassistant.data.UserPrefsStorage
import com.example.digitalstudentassistant.data.models.requests.CVRequest
import com.example.digitalstudentassistant.data.models.requests.LoginRequest
import com.example.digitalstudentassistant.data.models.responses.CVResponse
import com.example.digitalstudentassistant.data.models.responses.toCV
import com.example.digitalstudentassistant.data.network.ApiProvider
import com.example.digitalstudentassistant.data.network.ApiService
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.models.CV
import com.example.digitalstudentassistant.domain.repositories.CVRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CVRepositoryImpl(
    private val apiService: ApiService,
    private val userPrefsStorage: UserPrefsStorage
) : CVRepository {

    override suspend fun showAllCV(): OperationResult<List<CV>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.getAllCV().map {
                    it.toCV()
                }
                return@withContext OperationResult.Success(result)
            } catch (e: Throwable) {
                return@withContext OperationResult.Error(e.message)
            }
        }
    }

    override suspend fun addCV(cv: CVRequest): OperationResult<CV, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.addCV("Bearer ${userPrefsStorage.loadUserFromPrefs()!!.token}",
                    CVRequest(cv.nameCV,
                    cv.aboutInfo,
                    cv.school,
                    cv.university,
                    cv.workStatus,
                    cv.citizenship,
                    cv.language,
                    cv.workSchedule,
                    cv.skill,
                    cv.busyness)).toCV()
                return@withContext OperationResult.Success(result)
            } catch (e: Throwable) {
                return@withContext OperationResult.Error(e.message)
            }
        }
    }

    override suspend fun getUserCV(idUser: String): OperationResult<List<CV>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.getUserCV(idUser).map {
                    it.toCV()
                }
                return@withContext OperationResult.Success(result)
            } catch (e: Throwable) {
                return@withContext OperationResult.Error(e.message)
            }
        }
    }

    override suspend fun updateCV(
        cvId: String,
        cv: CVRequest
    ): OperationResult<CVResponse, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiService.updateCV("Bearer ${userPrefsStorage.loadUserFromPrefs()!!.token}",
                    cvId,
                    CVRequest(cv.nameCV,
                        cv.aboutInfo,
                        cv.school,
                        cv.university,
                        cv.workStatus,
                        cv.citizenship,
                        cv.language,
                        cv.workSchedule,
                        cv.skill,
                        cv.busyness))
                return@withContext OperationResult.Success(result)
            } catch (e: Throwable) {
                return@withContext OperationResult.Error(e.message)
            }
        }
    }
}