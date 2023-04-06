package ru.linew.spotifyApp.data.mappers

import ru.linew.spotifyApp.data.models.room.TrackEntity
import ru.linew.spotifyApp.ui.models.core.Track

fun Track.toTrackEntity(): TrackEntity{
    return TrackEntity(
        id = id,
        name = name,
        artist = artist,
        imageUrl = imageUrl
    )
}