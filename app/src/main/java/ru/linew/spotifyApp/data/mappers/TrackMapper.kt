package ru.linew.spotifyApp.data.mappers

import ru.linew.spotifyApp.data.models.room.TrackEntity
import ru.linew.spotifyApp.ui.models.core.Track

fun Track.toDataBaseEntity(): TrackEntity{
    return TrackEntity(
        this.id,
        this.name,
        this.artist,
        this.imageUrl
    )
}