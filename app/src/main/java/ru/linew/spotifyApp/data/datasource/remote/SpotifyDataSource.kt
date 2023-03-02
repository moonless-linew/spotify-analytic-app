package ru.linew.spotifyApp.data.datasource.remote

import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.api.SpotifyService
import ru.linew.spotifyApp.data.models.auth.Token
import ru.linew.spotifyApp.data.models.core.SearchResponseContainer
import ru.linew.spotifyApp.data.repository.datasource.remote.ISpotifyDataSource
import javax.inject.Inject

class SpotifyDataSource @Inject constructor(private val spotifyService: SpotifyService):
    ISpotifyDataSource {
    override fun searchTracks(searchString: String, token: Token): Single<SearchResponseContainer> {
        return spotifyService.searchTracks(searchString, "Bearer " + token.access_token)
    }
}