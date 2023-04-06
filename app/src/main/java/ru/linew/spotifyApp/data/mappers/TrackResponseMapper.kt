package ru.linew.spotifyApp.data.mappers

import ru.linew.spotifyApp.data.models.retrofit.core.Image
import ru.linew.spotifyApp.data.models.retrofit.core.TrackResponse
import ru.linew.spotifyApp.data.models.room.TrackEntity
import ru.linew.spotifyApp.ui.models.core.Track

fun TrackResponse.toUiLayer(): Track{
    return Track(
        id = id,
        name = name,
        artist = artists[0].name,
        album.images.takeIf(List<Image>::isNotEmpty)?.get(0)?.url ?: ""
    )
}

fun TrackResponse.toDataBaseEntity(): TrackEntity{
    return TrackEntity(
        id = id,
        name = name,
        artist = artists[0].name,
        album.images.takeIf(List<Image>::isNotEmpty)?.get(0)?.url ?: ""
    )
}