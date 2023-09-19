package com.whalescale.binit.models.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "type") val type: String,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "image") val image: String,
    @field:Json(name = "id") val id: Int
)
