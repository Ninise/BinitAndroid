package com.ndteam.wasteandroidapp.models.requests

data class SuggestRequest(
    val name: String,
    val type: String,
    val description: String,
    val location: String
)
