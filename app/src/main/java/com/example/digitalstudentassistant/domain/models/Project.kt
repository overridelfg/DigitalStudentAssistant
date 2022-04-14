package com.example.digitalstudentassistant.domain.models

import android.os.Parcelable
import com.example.digitalstudentassistant.data.models.responses.TagResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Project(
    val id: String,
    val title: String,
    val description: String,
    val communication: String,
    var creatorId: String,
    var tags: String
) : Parcelable