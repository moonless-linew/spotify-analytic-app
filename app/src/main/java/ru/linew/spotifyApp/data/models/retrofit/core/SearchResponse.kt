package ru.linew.spotifyApp.data.models.retrofit.core

data class SearchResponse(
    val href: String,
    val items: List<Track>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: Any,
    val total: Int
)