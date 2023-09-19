package com.whalescale.binit.models.states

data class SuggestionState(
    val suggestions: List<String>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)