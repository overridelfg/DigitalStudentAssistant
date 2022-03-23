package com.example.digitalstudentassistant.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Project(
    val name : String,
    val purpose: String,
    val description: String,
    val deadlineProjectDateFrom: String,
    val deadlineProjectDateTo: String,
    val deadlineTeamDateFrom: String,
    val deadlineTeamDateTo: String,
    val participantsNumber: Int,
    val status: String
) : Parcelable