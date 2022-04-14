package com.example.digitalstudentassistant.domain.repositories

import com.example.digitalstudentassistant.domain.OperationResult
import com.example.digitalstudentassistant.domain.models.CV

interface CVRepository {
    suspend fun showAllCV() : OperationResult<List<CV>, String?>

    suspend fun addCV(cv: CV) : OperationResult<CV, String?>
}