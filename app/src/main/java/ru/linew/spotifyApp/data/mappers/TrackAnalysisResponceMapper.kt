package ru.linew.spotifyApp.data.mappers

import ru.linew.spotifyApp.data.models.retrofit.core.TrackAnalysisResponse
import ru.linew.spotifyApp.ui.models.core.TrackAnalysis

fun TrackAnalysisResponse.toUiLayer(): TrackAnalysis{
    return TrackAnalysis(
         acousticness =  acousticness,
         danceability = danceability,
         duration_ms =  duration_ms,
         energy = energy,
         id = id,
         instrumentalness = instrumentalness,
         key = key,
         liveness = liveness,
         loudness = loudness,
         mode = mode,
         speechiness = speechiness,
        tempo = tempo,
        time_signature = time_signature,
        type = type,
        valence = valence
    )
}
