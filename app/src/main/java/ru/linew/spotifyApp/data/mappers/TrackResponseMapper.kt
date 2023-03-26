package ru.linew.spotifyApp.data.mappers

import ru.linew.spotifyApp.data.models.retrofit.core.TrackResponse
import ru.linew.spotifyApp.data.models.room.TrackEntity
import ru.linew.spotifyApp.ui.models.core.Track

fun TrackResponse.toUiLayer(): Track{
    return Track(
        this.id,
        this.name,
        this.artists[0].name,
        //а должно ли здесь это быть?
        if(this.album.images.isNotEmpty()) this.album.images[0].url else ""
    )
}

fun TrackResponse.toDataBaseEntity(): TrackEntity{
    return TrackEntity(
        this.id,
        this.name,
        this.artists[0].name,
        // и здесь
        if(this.album.images.isNotEmpty()) this.album.images[0].url else ""
    )
}