package ru.linew.spotifyApp.data.datasource.remote

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.models.retrofit.auth.Token
import ru.linew.spotifyApp.data.models.retrofit.core.TrackAnalysisResponce
import ru.linew.spotifyApp.data.retrofit.SpotifyService

class SpotifyDataSource @AssistedInject constructor(
    private val spotifyService: SpotifyService,
    @Assisted private val apiToken: Token
) {
    @AssistedFactory
    interface SpotifyDataSourceFactory {
        fun create(apiToken: Token): SpotifyDataSource
    }

    fun analysisTrack(id: String): Single<TrackAnalysisResponce> {
        return spotifyService.analysisTrack(id, "Bearer " + apiToken.access_token)
    }
}
