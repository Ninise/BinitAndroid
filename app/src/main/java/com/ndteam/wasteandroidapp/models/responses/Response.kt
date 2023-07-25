package com.ndteam.wasteandroidapp.models.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Response<T>(
    @field:Json(name = "status") val status: Boolean,
    @field:Json(name = "code") val code: Int,
    @field:Json(name = "data") val data: T? = null,
    @field:Json(name = "error") val error: String? = null
)
