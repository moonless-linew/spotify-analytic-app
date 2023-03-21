package ru.linew.spotifyApp.data.mappers

import ru.linew.spotifyApp.data.models.room.TrackEntity
import ru.linew.spotifyApp.ui.models.core.Track

fun TrackEntity.toUiLayer(): Track {
    return Track(
        this.id, this.name, this.artist, this.imageUrl
    )
}