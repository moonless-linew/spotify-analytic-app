package ru.linew.spotifyApp.data.mappers

import ru.linew.spotifyApp.data.models.retrofit.core.Track

object TrackMapper {
    fun transform(data: Track): ru.linew.spotifyApp.ui.models.core.Track {
        return ru.linew.spotifyApp.ui.models.core.Track(
            data.id,
            data.name,
            data.artists[0].name,
            if(data.album.images.isNotEmpty()) data.album.images[0].url else ""
        )
    }
}