package ru.linew.spotifyApp.data.repository

import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.repository.datasource.remote.ISpotifyDataSource
import ru.linew.spotifyApp.ui.models.Track
import ru.linew.spotifyApp.ui.repository.ISpotifyRepository
import javax.inject.Inject


class SpotifyRepository @Inject constructor(
    private val spotifyApiDataSource: ISpotifyDataSource,
    private val authRepository: AuthRepository
) :
    ISpotifyRepository {
    override fun searchTracks(searchString: String): Single<List<Track>> {
        return authRepository
            .getToken()
            .flatMap {
                spotifyApiDataSource
                    .searchTracks(searchString, it)
                    .onErrorResumeNext {
                        authRepository.getToken(true).flatMap { token ->
                            spotifyApiDataSource.searchTracks(
                                searchString,
                                token = token
                            )
                        }
                    }
            }
            .map {
                it.searchResponse.items.map { item ->
                    Track(item.name, item.artists[0].name, item.album.images[0].url)
                }
            }

    }


}