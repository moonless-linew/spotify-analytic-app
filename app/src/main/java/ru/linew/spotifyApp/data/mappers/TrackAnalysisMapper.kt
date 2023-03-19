package ru.linew.spotifyApp.data.mappers

import ru.linew.spotifyApp.data.models.retrofit.core.TrackAnalysisResponse
import ru.linew.spotifyApp.ui.models.core.TrackAnalysis

object TrackAnalysisMapper {
    fun transform(track: TrackAnalysisResponse): TrackAnalysis{
        return TrackAnalysis(
            track.acousticness,
            track.danceability,
            track.duration_ms,
            track.energy,
            track.id,
            track.instrumentalness,
            track.key,
            track.liveness,
            track.loudness,
            track.mode,
            track.speechiness,
            track.tempo,
            track.time_signature,
            track.type,
            track.valence
        )
    }
}