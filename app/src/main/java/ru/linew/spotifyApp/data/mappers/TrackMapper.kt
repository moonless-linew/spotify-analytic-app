package ru.linew.spotifyApp.data.mappers

import ru.linew.spotifyApp.data.models.core.Track

object TrackMapper {
    fun transform(data: Track): ru.linew.spotifyApp.ui.models.Track{
        return ru.linew.spotifyApp.ui.models.Track(
            data.id,
            data.name,
            data.artists[0].name,
            data.album.images[0].url
        )
    }
}