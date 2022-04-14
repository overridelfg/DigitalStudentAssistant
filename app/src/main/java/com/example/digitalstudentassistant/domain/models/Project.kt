package com.example.digitalstudentassistant.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Project(
    val id : Long,
    val name : String,
    val purpose: String,
    val description: String,
    val status: String,
) : Parcelable