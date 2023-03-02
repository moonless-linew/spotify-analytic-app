package ru.linew.spotifyApp.data.repository.datasource.remote

import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.models.auth.Token
import ru.linew.spotifyApp.data.models.core.SearchResponseContainer

interface ISpotifyDataSource {
    fun searchTracks(searchString: String, token: Token): Single<SearchResponseContainer>
}