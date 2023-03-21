package ru.linew.spotifyApp.data.mappers

import ru.linew.spotifyApp.data.models.retrofit.core.TrackAnalysisResponse
import ru.linew.spotifyApp.ui.models.core.TrackAnalysis

fun TrackAnalysisResponse.toUiLayer(): TrackAnalysis{
    return TrackAnalysis(
        this.acousticness,
        this.danceability,
        this.duration_ms,
        this.energy,
        this.id,
        this.instrumentalness,
        this.key,
        this.liveness,
        this.loudness,
        this.mode,
        this.speechiness,
        this.tempo,
        this.time_signature,
        this.type,
        this.valence
    )
}
