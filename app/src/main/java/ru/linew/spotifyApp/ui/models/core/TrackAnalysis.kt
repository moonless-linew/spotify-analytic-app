package ru.linew.spotifyApp.ui.models.core

data class TrackAnalysis(
    val acousticness: Double,
    val danceability: Double,
    val duration_ms: Int,
    val energy: Double,
    val id: String,
    val instrumentalness: Double,
    val key: Int,
    val liveness: Double,
    val loudness: Double,
    val mode: Int,
    val speechiness: Double,
    val tempo: Double,
    val time_signature: Int,
    val type: String,
    val valence: Double
)
