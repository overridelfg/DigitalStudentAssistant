package com.example.digitalstudentassistant.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CV(
    val nameCV : String,
    val aboutInfo : String,
    val school : String,
    val university : String,
    val workStatus : String,
    val citizenship : String,
    val language: String,
    val workSchedule: String,
    val skill : String,
    val busyness : String
) : Parcelable