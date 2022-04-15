package com.example.digitalstudentassistant.domain.repositories

import com.example.digitalstudentassistant.data.models.requests.CVRequest
import com.example.digitalstudentassistant.data.models.responses.CVResponse
import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.models.CV

interface CVRepository {
    suspend fun showAllCV() : OperationResult<List<CV>, String?>

    suspend fun addCV(cv: CVRequest) : OperationResult<CV, String?>

    suspend fun getUserCV(idUser : String) : OperationResult<List<CV>, String?>

    suspend fun updateCV(cvId: String, cv: CVRequest) : OperationResult<CVResponse, String?>
}