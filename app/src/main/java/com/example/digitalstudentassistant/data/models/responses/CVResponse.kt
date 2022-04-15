package com.example.digitalstudentassistant.data.models.responses

import com.example.digitalstudentassistant.domain.models.CV

data class CVResponse(
    val id: String,
    val nameCV: String,
    val aboutInfo: String,
    val school: String,
    val university: String,
    val workStatus: String,
    val citizenship: String,
    val language: String,
    val workSchedule: String,
    val skill: String,
    val busyness : String
)
fun CVResponse.toCV(): CV {
    return CV(
        id,
        nameCV,
        aboutInfo,
        school,
        university,
        workStatus,
        citizenship,
        language,
        workSchedule,
        skill,
        busyness
    )
}
