package com.example.digitalstudentassistant.data.models.responses.project

import com.example.digitalstudentassistant.data.models.responses.TagResponse

data class Tag(
    val about: String,
    val id: String,
    val name: String
)

fun Tag.toTagResponse() : TagResponse =
    TagResponse(
        id, name, about
    )