package ru.linew.spotifyApp.ui.repository

import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.ui.models.Track


interface ISpotifyRepository {
//    fun getTrack(uid: String): Single<Track>
    fun searchTracks(searchString: String): Single<List<Track>>
}